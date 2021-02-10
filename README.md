### High Performance Leaderboard 



* This project is made for an interview case where all responses should be under 1 second.

* Current version is tested with 5,000,000 unique players and works under 1 second in each operation.

* Redis Sorted Sets are used in order to provide faster ranking ( **O(log N)** ).

* Each user is saved to two different sorted sets as **World** and **COUNTRY_ISO_CODE** to be able to give local rankings quickly.
