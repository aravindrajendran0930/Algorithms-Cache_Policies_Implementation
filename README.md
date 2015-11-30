# cache-implementations-algorithms
------------------------------------
Author: Aravind Rajendran
---------------------------

The JAVA programs are to implement cache policies of LRU and ARC.
They take in a sample .lis file for processing and output the statistics of the cache algorithms' implementation

Steps to be followed to execute the code and view the results:
---------------------------------------------------------------

1. Compile and run LRU_CachePolicy.java
2. When prompted in the console, enter the sample file for test and the cache size of the configuration.
    2.1. Enter the fileName of the file
    2.2. Enter the cache size of the cache configuration (e.g. 1024, 2048, 4096...)
3. Compile and run ARC_CachePolicy.java
4. When prompted in the console, enter the sample file for test and the cache size of the configuration.
    4.1. Enter the fileName of the file
    4.2. Enter the cache size of the cache configuration (e.g. 1024, 2048, 4096...)

The result set comprises:
------------------------------

1. The number of 'Hits' occurred by the cache configuration.
2. The number of 'Misses' occurred by the cache configuration.
3. The 'Hit-Ratio' of the cache configuration in percentage.
