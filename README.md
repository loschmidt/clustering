#Average Link Agglomerative Clustering Library

This is Java implementation of efficient algorithm for agglomerative hierarchical clustering by average linkage method (also known as UPGMA). It runs in O(n^2) time and memory and allows to represent the distance matrix as two dimensional array of floats with very little overhead (no data structures for keeping sorted distances is needed).

Basic algorithm is capable of clustering more than 60,000 instances within an hour on ordinary notebook.

Additionally, memory efficient mode is implemented, allowing to cluster more than 100,000 in several hours.

Several approximations are being prepared to allow clustering of about 1,000,000 instances, however these might not be suitable for all types of data.
