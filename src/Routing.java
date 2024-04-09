
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

//Can get the starting coordinates through the wire id and get steps method

        for(Endpoints endpoint : goals){
            //if endpoints are adjacent
            int[] occurrences = new int[id];

            if(endpoint.start.isAdjacent(endpoint.end)){
                Wire wire = new Wire(id);
                wire.add(endpoint.start);
                wire.add(endpoint.end);
                wires.add(wire);
                id++;
                continue;
            }
            //Find the best route       [Also check if there is no best route]

            /*
            You could go through removing wires with the most occurrences that don't allow the current to work and then
             */
            List<Coord> bestRoute = BFS(board, endpoint, occurrences, id);
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
                    bestRoute = BFS(board, endpoint, occurrences, id);
                }

                resetOccurrences(occurrences, -1);
//                System.out.println("Starting: " + endpoint.start + " Ending: " + endpoint.end);
//                System.out.println("Returning nothing");

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
                    List<Coord> route = BFS(board, endpoints, occurrences, ID);
                    Wire newWire = new Wire(ID, route);
                    wires.add(newWire);
                    board.placeWire(newWire);
                }
            }

//            for(int i = 0; i < occurrences.length; i++){
//                System.out.println(i + " : " + occurrences[i]);
//            }
//            for(int i = 0; i < pencilMarks.size(); i++){
//                System.out.print(i + " : ");
//                for(int j = 0; j < pencilMarks.get(i).size(); j++){
//                    System.out.print(pencilMarks.get(i).get(j) + " ");
//                }
//                System.out.println();
//            }

            //Reset the occurrences
            resetOccurrences(occurrences, -1);
        }
        return wires;
    }

    //Finding the shortest path including the start to the finish
    public static List<Coord> BFS(Board board, Endpoints endpoints, int[] occurrences, int id){
        Map<Coord, Coord> map = new HashMap<>();
        ArrayList<Coord> visited = new ArrayList<>();
        ArrayList<Coord> obstacleVisited = new ArrayList<>();
        Queue<Coord> queue = new LinkedList<>();
        boolean found = false;

        Coord start = endpoints.start;
        Coord end = endpoints.end;
        map.put(start, null);
        queue.add(start);

//        System.out.println("Starting point: " + start);
//        System.out.println("Goal: " + end);

        while(!queue.isEmpty()){
            Coord parent = queue.poll();
            ArrayList<Coord> list = board.adj(parent);
            //At most this loop will run 4 times
            for(Coord coord: list){
                int value = board.getValue(coord); //find the id of the occupied coord
                if(board.isOccupied(coord) && !board.isObstacle(coord) && !obstacleVisited.contains(coord)){
                    if(value < id) {
                        occurrences[value - 1]++;
                        obstacleVisited.add(coord);
                    }
                }
                if((!board.isObstacle(coord) && !board.isOccupied(coord) && !visited.contains(coord)) || coord.equals(end)){
//                    System.out.println("Coord inside : " + coord);
                    map.put(coord, parent);
                    visited.add(coord);
                    //Check if we have reached the end or found what we needed
                    if(coord.equals(end)){
//                        System.out.println("Found!!!");
                        found = true;
                        break;
                    }
                    else {
//                        System.out.println("Adding adjacency: ");
//                        for(Coord c : board.adj(coord)){
//                            System.out.println("Adj Coord : " + c);
//                        }
                        queue.add(coord);
                    }
                }
//                if(queue.isEmpty()) System.out.println("queue is empty");
//                else System.out.println("queue is not empty ------");
            }
            //found what we need
            if(found){
//                System.out.println("FOUND!!!");
                break;
            }
//            if(queue.isEmpty()) System.out.println("queue is empty");
        }

//        for(Coord coord : map.keySet()){
//            System.out.println("Key: " + coord);
//            System.out.println("Parent: " + map.get(coord));
//        }

        ArrayList<Coord> route = new ArrayList<>();
        reconstruct(route, end, map);
        reverse(route, 0, route.size() - 1);

        if(map.isEmpty()) System.out.println("Map is empty");

//        for(Coord coord : route){
//            System.out.print(coord + "--->");
//        }
        System.out.println();
        return route;
    }

    public static void reconstruct(ArrayList<Coord> route, Coord end, Map<Coord, Coord> map){
        Coord coord = end;
        while (map.containsKey(coord)) {
            route.add(coord);
            coord = map.get(coord);
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
