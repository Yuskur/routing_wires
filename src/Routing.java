import jdk.internal.util.xml.impl.Pair;

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
        for(Endpoints endpoint : goals){
            //if endpoints are adjacent
            if(endpoint.start.isAdjacent(endpoint.end)){
                System.out.println("They are adjacent");
                Wire wire = new Wire(id);
                wire.add(endpoint.start);
                wire.add(endpoint.end);
                wires.add(wire);
                id++;
                continue;
            }
            //Find the best route       [Also check if there is no best route]
            List<Coord> bestRoute = BFS(board, endpoint);
            if(bestRoute.isEmpty()) return null;

            for(Coord coord : bestRoute){
                System.out.print(coord + "-->");
            }
            System.out.println();

            //Place wires on the board
            Wire wire = new Wire(id, bestRoute);
            wires.add(wire);
            board.placeWire(wire);
            id++;
        }

        return wires;
    }

    public static class Relation<K, W>{
        K parent;
        W list;

        public Relation(K parent, W list){
            this.parent = parent;
            this.list = list;
        }
    }

    //Finding the shortest path including the start to the finish
    public static List<Coord> BFS(Board board, Endpoints endpoints){
        Map<Coord, Coord> map = new HashMap<>();
        ArrayList<Coord> visited = new ArrayList<>();
        Queue<Relation<Coord, ArrayList<Coord>>> queue = new LinkedList<>();
        boolean found = false;

        Coord start = endpoints.start;
        Coord end = endpoints.end;
        Relation<Coord, ArrayList<Coord>> relation = new Relation<>(start, board.adj(start));
        map.put(start, null);
        queue.add(relation);

        System.out.println("Starting point: " + start);
        System.out.println("Goal: " + end);

        while(!queue.isEmpty()){
            Relation<Coord, ArrayList<Coord>> list = queue.poll();
            for(Coord c : list.list){
                System.out.println("Adj Coord : " + c);
            }
            //At most this loop will run 4 times
            for(Coord coord: list.list){
                if((!board.isObstacle(coord) && !board.isOccupied(coord) && !visited.contains(coord)) || coord.equals(end)){
                    System.out.println("Coord inside : " + coord);
                    visited.add(coord);
                    map.put(coord, list.parent);
                    //Check if we have reached the end or found what we needed
                    if(coord.equals(end)){
                        System.out.println("Found!!!");
                        found = true;
                        break;
                    }
                    else {
                        System.out.println("Adding adjacency: ");
                        for(Coord c : board.adj(coord)){
                            System.out.println("Adj Coord : " + c);
                        }
                        Relation<Coord, ArrayList<Coord>> rel = new Relation<>(coord, board.adj(coord));
                        queue.add(rel);
                    }
                }
                if(queue.isEmpty()) System.out.println("queue is empty");
                else System.out.println("queue is not empty ------");
            }
            //found what we need
            if(found){
                System.out.println("found");
                break;
            }
            if(queue.isEmpty()) System.out.println("queue is empty");
        }

//        for(Coord coord : map.keySet()){
//            System.out.println("Key: " + coord);
//            System.out.println("Parent: " + map.get(coord));
//        }

        ArrayList<Coord> route = new ArrayList<>();
        reconstruct(route, end, map);
        reverse(route);

        if(map.isEmpty()) System.out.println("Map is empty");

        for(Coord coord : route){
            System.out.print(coord + "--->");
        }
        System.out.println();
        return route;
    }

    public static void reconstruct(ArrayList<Coord> route, Coord end, Map<Coord, Coord> map){
        Coord coord = end;
        if(!map.containsKey(end)) {
            System.out.println("Map does not contain the end");
            return;
        }
        while(true){
            if(map.get(coord) == null) {
                route.add(coord);
                break;
            }
            route.add(coord);
            coord = map.get(coord);
        }
    }

    //Just reverse the given list
    public static void reverse(ArrayList<Coord> route){
        Collections.reverse(route);
    }
}
