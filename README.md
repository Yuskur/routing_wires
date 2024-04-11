Routing Wires Student Support Code

# Overview of the algorithm

* The findPaths method takes in a board and a list of endpoints and is tasked in finding the optimal paths for every endpoint.
* The list of endpoints is then iterated through and will be routed on the board wired through a wire object. - [O(n) time]
* If two Coords in an endpoint are adjacent then they are routed with a wire and given a unique id incrementing from 1. - [O(1) time]
* If the two endpoints are not adjacent then try and find the best route calling BFS
  * The BFS method takes a board, endpoints, an int array of occurrances, id of current enpoints, and an empty list of visited coords. Its objective is to find the fastest route between two endpoints
  * It does this by roaming through all valid vertecies (not occupied, not obstical, not visited or is end coord) and populating them inside of a queue as nodes (nodes hold coord and parent node) to check their adjacent coords - [O(v) time]
    * Each valid adjacent vertex will be added to visited and will be made a node storing its coord and its parent 
    * If a vertex is equal to the end we know we have reached the goal with the fastest route and so we call reconstruct which takes an empty list and the end node populating the list starting from the node and going back with its parent node until reahcing null indicating we have found the starting node. - [O(E) time]
    * Once reconstructing the route we reverse the route to have the list going form starting coord to ending coord. - [O(E) time]
  * If vertex is occupied, not an obstical and hasn't already been visited, we would then check if the occupied vertex value is smaller than the id of the goal. If so then it adds an occurrance - [O(1) time]
* Overall time complexity [BFS] : [O(V + E) time]
* Assuming that the bestRoute called by BFS comes out empty we would remove the wire with the most occurrences and add it to the removed wires array and test the best route again. This will continue to loop at most the number of total previous routes formed. If bestRoute for the current wire is empty then return null otherwise add it to the board. - [O(V + E) time]
* place the best route on the board. - [O(n) time]
* The removed wires array will then be checked to see if any wires have been removed then re-route them. - [O(n(V + E)) time]

Overall Time Complexity: [O(n(V + E))]

# Applying algorithm to interesting boards / finding and minimizing wire layouts evaluation

* [Case 1]
  * ![image](https://github.com/Yuskur/routing_wires/assets/123311946/e4614fd3-0aaa-4d70-8255-83edc27e0d12)
  
  * In this case you can see that 1 has its fastest route to the left side.
  * This path ultimately cuts the shortest and only path for 2 and so it should re-route 1 and route 2
  * Removing and re-routing 1 assures us that we are getting the second shortest path.
 
  * ![image](https://github.com/Yuskur/routing_wires/assets/123311946/f8e92027-88a0-4207-b0eb-d80051b7e09e)

* [Case 2]
  * ![image](https://github.com/Yuskur/routing_wires/assets/123311946/6289a76f-b942-4a63-b11e-2aacdfe9bd38)
 
  * In this case you can see that when each path will take its shortest path it will block off the path allowing 4 to reach its destination. This will ultimately lead to 4 removing 3 wires before reaching its destination if possible.
  * 1 will be the first to re-route taking its second shortest available path clearing a way for route 4.
  * 2 will then follow to re-route taking its second shortest available path clearing a way for route 4.
  * afterwards 3 will follow to re-route taking its second shortest available path clearing a way for route 4.
  * As a result the shortest available path for 4 is taken while also using the second best routes for routes that cleared the way for route 4.

  * ![image](https://github.com/Yuskur/routing_wires/assets/123311946/87e14513-9a0e-4c2d-8de6-cb7f6b9422f2)


# Time complexity / Wall clock time

### Time complaxity
 * As I talked about in the algorithm overview the time complexity is O(n(V + E))

### Wall Clock time

* Small input size
  * Height: 10
  * Width: 10
  * Wall-clock time: 5 milliseconds
 
* Large input size
  * Height: 500
  * Width: 500
  * Wall-clock time: 



Revision: Fall 2023
