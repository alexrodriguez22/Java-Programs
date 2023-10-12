one of the two .txt files will be used as input and the user will input the substring to search for

String-Searching Algorithms

1. Knuth-Morris-Pratt (KMP) Algorithm

Purpose: The KMP algorithm is designed to search for a substring (or pattern) within a main string (or text) in linear time, without the need for backtracking.

How it Works:

KMP utilizes a partial match table (also called the "failure function") which contains information about the proper next position of the characters in the substring.
By precomputing this table, the algorithm ensures that comparisons done with characters of the main string are never repeated.
Advantages:

Efficient with a worst-case performance of O(n), where n is the length of the main string.
No backtracking.

2. Boyer-Moore Algorithm

Purpose: The Boyer-Moore algorithm is another efficient string-searching (or substring searching) method that skips sections of the main string, resulting in a lower number of operations on average compared to naive search.

How it Works:

Boyer-Moore uses two primary heuristics, the Bad Character Heuristic and the Good Suffix Heuristic, to skip sections of the text, thus speeding up the search process. This program only utilizes the Bad Character Heuristic.

The algorithm preprocesses the substring to create these heuristics.
Advantages:

Often performs faster in practice than other linear-time searching algorithms.
Can work very efficiently for large patterns and large alphabets.
