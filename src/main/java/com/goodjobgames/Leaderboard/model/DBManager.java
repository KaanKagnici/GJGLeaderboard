package com.goodjobgames.Leaderboard.model;


import com.goodjobgames.Leaderboard.model.domain.Player;
import com.goodjobgames.Leaderboard.model.domain.ScoreSubmission;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.io.BufferedReader;
import java.util.Locale;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;


public class DBManager {


    public DBManager() {

    }


    private boolean userExists(String id, Jedis jedis) {

        return jedis.hexists(id, "name");


    }


    public boolean createPlayer(Player player) {


        try (Jedis jedis = JedisFactory.getInstance().getResource()) {

            String id = player.getUser_id();
            String name = player.getDisplay_name();
            String country = player.getCountry().toLowerCase();
            double score = player.getPoints();

            if (userExists(id, jedis)) {
                return false;
            } else {
                Map<String, String> map = new HashMap<String, String>() {{
                    put("name", name);
                    put("country", country);
                }};

                jedis.hmset(id, map); // Make name and country data accessible in O(1) time.

                jedis.zadd("world", score, id); // Add to global ranking
                jedis.zadd(country, score, id); // Add to local ranking

                return true;
            }
        }


    }

    public Player getPlayer(String id) {

        try (Jedis jedis = JedisFactory.getInstance().getResource()) {
            if (userExists(id, jedis)) {
                List<String> nameAndCountry = jedis.hmget(id, "name", "country");

                double score = jedis.zscore("world", id);
                long rank = jedis.zrevrank("world", id) + 1; // +1 since 0 indexed ranking


                return new Player(id, nameAndCountry.get(0), nameAndCountry.get(1), score, rank);
            } else {
                return null;
            }
        }


    }


    public boolean submitScore(ScoreSubmission scoreSubmission) {

        try (Jedis jedis = JedisFactory.getInstance().getResource()) {
            String id = scoreSubmission.getUser_id();
            double score = scoreSubmission.getScore_worth();


            if (userExists(id, jedis)) {
                jedis.zincrby("world", score, id); // Submission for global ranking

                String country = jedis.hget(id, "country");
                jedis.zincrby(country, score, id);// Submission for local ranking.
                return true;
            } else {
                return false;
            }
        }


    }


    public List<Player> getLeaderboard(int page) {

        try (Jedis jedis = JedisFactory.getInstance().getResource()) {

            int end = page == 0 ? 1000 : page * 1000; // If page variable is not provided return first page.

            Set<Tuple> leaderboard = jedis.zrevrangeWithScores("world", end - 1000, end);
            List<Player> result = new LinkedList<>();


            int rank = (end - 1000) + 1; // +1 due to 0-based ranking
            for (Tuple player : leaderboard) {
                String id = player.getElement();

                List<String> nameAndCountry = jedis.hmget(id, "name", "country");
                result.add(new Player(id, nameAndCountry.get(0), nameAndCountry.get(1), player.getScore(), rank));
                rank++;

            }
            return result;
        }
    }


    public List<Player> getLocalLeaderboard(String country, int page) {

        try (Jedis jedis = JedisFactory.getInstance().getResource()) {
            int end = page == 0 ? 1000 : page * 1000; // If page variable is not provided return first page.

            Set<Tuple> leaderboard = jedis.zrevrangeWithScores(country, end - 1000, end);
            List<Player> result = new LinkedList<>();

            for (Tuple player : leaderboard) {

                String id = player.getElement();

                String name = jedis.hget(id, "name");
                result.add(new Player(id, name, country, player.getScore(), jedis.zrevrank("world", id) + 1));


            }


            return result;
        }
    }

    public void populate(int num) {

        BufferedReader reader;
        Random randGen;
        String[] countries = Locale.getISOCountries();


        int countryNum = countries.length;
        randGen = new Random();

        for (int i = 0; i < num; i++) {


            String id = UUID.randomUUID().toString();
            String country = countries[randGen.nextInt(countryNum)].toLowerCase(Locale.ROOT);
            Player player = new Player(id, id + "_name", country, randGen.nextInt(10000));

            createPlayer(player);
        }


    }

    public void flushDB() {
        try (Jedis jedis = JedisFactory.getInstance().getResource()) {
            jedis.flushAll();
        }
    }


}
