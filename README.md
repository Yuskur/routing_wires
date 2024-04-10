Routing Wires Student Support Code

## Overview of the algorithm

* The findPaths method takes in a board and a list of endpoints and is tasked in finding the optimal paths for every endpoint.
* The list of endpoints is then iterated through and will be routed on the board wired through a wire object. - O(n) time
* If two Coords in an endpoint are adjacent then they are routed with a wire and given a unique id incrementing from 1. - O(1) time
* If the two endpoints are not adjacent then try and find the best route calling BFS
  * The BFS method takes a board, endpoints, an int array of occurrances, id of current enpoints, and an empty list of visited coords. Its objective is to find the fastest route between two endpoints
  * It does this by roaming through all valid vertecies (not occupied, not obstical, not visited or is end coord) and populating them inside of a queue as nodes (nodes hold coord and parent node) to check their adjacent coords - O(v)
    * Each valid adjacent vertex will be added to visited and will be made a node storing its coord and its parent 
    * If a vertex is equal to the end we know we have reached the goal with the fastest route and so we call reconstruct which takes an empty list and the end node populating the list starting from the node and going back with its parent node until reahcing null indicating we have found the starting node. - O(E)
    * Once reconstructing the route we reverse the route to have the list going form starting coord to ending coord. - O(E)
  * If vertex is occupied, not an obstical and hasn't already been visited, we would then check if the occupied vertex value is smaller than the id of the goal. If so then it adds an occurrance - O(1)
* Overall time complexity [BFS] : O(V + E)
* Assuming that the bestRoute called by BFS comes out empty we would remove the wire with the most occurrences and add it to the removed wires array and test the best route again. This will continue to loop until the bestRoute for the current wire is successful. - O(n(V + E))
* Afterward place the best route on the board. - O(n)
* The removed wires array will then be checked to see if any wires have been removed then re-route them. - O(n(V + E))

Overall Time Complexity: O(n(V + E))

## Applying algorithm to interesting boards

* [Case 1]
  * ![image](https://github.com/Yuskur/routing_wires/assets/123311946/e4614fd3-0aaa-4d70-8255-83edc27e0d12)
  
  * In this case you can see that 1 has its fastest route to the left side.
  * This path ultimately cuts the shortest and only path for 2 and so it should re-route 1 and route 2
  * Removing and re-routing 1 gives us the second shortest path so we can be assured we are getting the second shortes path
 
  * ![image](https://github.com/Yuskur/routing_wires/assets/123311946/f8e92027-88a0-4207-b0eb-d80051b7e09e)

* [Case 2]
  * 




Revision: Fall 2023
