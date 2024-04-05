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
        //Can try the shortest path algorithm along with checking for -1's and 0's
        //pencil marks should be the adjacent paths
        for(Endpoints endpoint : goals){
            //Find the best route       [Also check if there is no best route]
            List<Coord> bestRoute = BFS(board, endpoint);
            if(bestRoute.isEmpty()) return null;

        }

        return null;
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

        while(!queue.isEmpty()){
            Relation<Coord, ArrayList<Coord>> list = queue.poll();
            //At most this loop will run 4 times
            for(Coord coord: list.list){
                if(!board.isObstacle(coord) && !board.isOccupied(coord) && !visited.contains(coord)){
                    visited.add(coord);
                    map.put(coord, list.parent);
                    //Check if we have reached the end or found what we needed
                    if(coord.equals(end)){
                        found = true;
                        break;
                    }
                    else queue.add(new Relation<>(coord, board.adj(coord)));
                }
            }
            //found what we need
            if(found){
                break;
            }
        }

        ArrayList<Coord> route = new ArrayList<>();
        reconstruct(route, end, map);
        reverse(route);
        return route;
    }

    public static void reconstruct(ArrayList<Coord> route, Coord end, Map<Coord, Coord> map){
        Coord coord = end;
        if(!map.containsKey(end)) return;
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
