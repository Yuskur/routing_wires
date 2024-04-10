import java.util.*;

public class Routing {

    /**
     * TODO
     * <p>
     * The findPaths function takes a board and a list of goals that contain
     * endpoints that need to be connected. The function returns a list of
     * Paths that connect the points.
     */
    public static ArrayList<Wire>
    findPaths(Board board, ArrayList<Endpoints> goals) {
        int id = 1;
        ArrayList<Wire> wires = new ArrayList<>();
        int[] occurrences = new int[goals.size()];
        List<Coord> bestRoute;
        ArrayList<Coord> visited = new ArrayList<>();

        for(Endpoints endpoint : goals){
            //if endpoints are adjacent

            if(endpoint.start.isAdjacent(endpoint.end)){
                Wire wire = new Wire(id);
                wire.add(endpoint.start);
                wire.add(endpoint.end);
                wires.add(wire);
                id++;
                continue;
            }

            bestRoute = BFS(board, endpoint, occurrences, id, visited);
            Wire[] removedWires = new Wire[id];
            if(bestRoute.isEmpty()) {

                //While its empty
                while(bestRoute.isEmpty()){
                    int mostOccurrences = findMax(occurrences);
                    if(mostOccurrences + 1 > id) return null;
                    Wire wire = wires.get(mostOccurrences);
                    board.removeWire(wire);
                    removedWires[mostOccurrences] = wire;
                    resetOccurrences(occurrences, mostOccurrences);
                    visited = new ArrayList<>();
                    bestRoute = BFS(board, endpoint, occurrences, id, visited);
                }

                resetOccurrences(occurrences, -1);
                //Clear the map
                //Clear the visited coords
                visited = new ArrayList<>();
            }

            //Place wires on the board
            Wire wire = new Wire(id, bestRoute);
            wires.add(wire);
            board.placeWire(wire);
            id++;

            for(Wire w : removedWires){
                if(w != null){
                    wires.remove(w);
                    int ID = board.getValue(w.start());
                    Endpoints endpoints = new Endpoints(ID, w.start(), w.end());
                    List<Coord> route = BFS(board, endpoints, occurrences, ID, visited);
                    Wire newWire = new Wire(ID, route);
                    wires.add(newWire);
                    board.placeWire(newWire);
                }
            }

            //Reset the occurrences
            resetOccurrences(occurrences, -1);
        }

        return wires;
    }

    static class Node<K>{
        K coord;
        Node<K> parent;

        public Node(K coord, Node<K> parent){
            this.coord = coord;
            this.parent = parent;
        }
    }

    //Finding the shortest path including the start to the finish
    public static List<Coord> BFS(Board board, Endpoints endpoints, int[] occurrences,
                                  int id, ArrayList<Coord> visited){
        Queue<Node<Coord>> queue = new LinkedList<>();

        Coord start = endpoints.start;
        Coord end = endpoints.end;
        Node<Coord> s = new Node<>(start, null);
        queue.add(s);


        while(!queue.isEmpty()){
            Node<Coord> parent = queue.poll();
            //At most this loop will run 4 times
            for(Coord coord: board.adj(parent.coord)){
                int value = board.getValue(coord); //find the id of the occupied coord
                if(board.isOccupied(coord) && !board.isObstacle(coord) && !visited.contains(coord)){
                    if(value < id) {
                        occurrences[value - 1]++;
                        visited.add(coord);
                    }
                }
                if((!board.isObstacle(coord) && !board.isOccupied(coord) && !visited.contains(coord)) || coord.equals(end)){

                    visited.add(coord);
                    Node<Coord> node = new Node<>(coord, parent);
                    //Check if we have reached the end or found what we needed
                    if(coord.equals(end)){
                        ArrayList<Coord> route = new ArrayList<>();
                        reconstruct(route, node);
                        reverse(route, 0, route.size() - 1);
                        return route;
                    }
                    else {
                        queue.add(node);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    public static void reconstruct(ArrayList<Coord> route, Node<Coord> end){
        Node<Coord> coord = end;
        while (coord != null) {
            route.add(coord.coord);
            coord = coord.parent;
        }
    }


    //Just reverse the given list
    public static void reverse(ArrayList<Coord> route, int start, int end){
        if(start >= end){
            return;
        }
        Coord temp = route.get(start);
        route.set(start, route.get(end));
        route.set(end, temp);
        reverse(route, start + 1, end - 1);
    }

    public static int findMax(int[] occurrences){
        int currentMax = occurrences[0];
        int currentMaxIndex = 0;
        for(int i = 1; i < occurrences.length; i++){
            if(currentMax < occurrences[i]){
                currentMax = occurrences[i];
                currentMaxIndex = i;
            }
        }
        return currentMaxIndex;
    }

    public static void resetOccurrences(int[] occurrences, int id){
        for(int i = 0; i < occurrences.length; i++){
            if(i == id || id == -1) occurrences[i] = 0;
            else occurrences[i] = 1;
        }
    }
}