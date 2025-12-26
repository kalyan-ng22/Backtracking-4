// Time Complexity : O(k ^ (n/k)) - k is the average group size and N is the length of string s.
// Space Complexity : O(k ^ (n/k))
// Did this code successfully run on Leetcode : Yes
// Approach : We need to explore all the combinations that are possible by the groups, this can be done by DFS by building all possible paths
// along with the backtracking. Before that we can manipulate the input string separted by groups so that path can be explored with all
// combinations from the group's characters.

class Solution {
    List<String> result;
    public String[] expand(String s) {
        this.result = new ArrayList<>();
        List<List<Character>> groups = new ArrayList<>();
        int i = 0;
        while(i < s.length()){ //parse the input string
            List<Character> group = new ArrayList<>();
            if(s.charAt(i) == '{'){ //start appending when { is found
                i++;
                while(s.charAt(i) != '}'){
                    if(s.charAt(i) != ','){
                        group.add(s.charAt(i)); //append all chars in a group to one list
                    }
                    i++;
                }
                i++;
            }else{
                group.add(s.charAt(i)); //individual characters in individual group
                i++;
            }

            Collections.sort(group); //sorts in lexograpgic order

            groups.add(group);
        }

        dfs(groups, 0, new StringBuilder()); //dfs and backtrack to get all combinations for the groups
        return result.toArray(new String[0]);
    }

    private void dfs(List<List<Character>> groups, int idx, StringBuilder path){
        if(idx == groups.size()){ //reached end of group
            result.add(path.toString()); //add to result
            return;
        }
        List<Character> currGroup = groups.get(idx);
        for(int i=0; i<currGroup.size(); i++){ //loop current group
            int le = path.length();
            path.append(currGroup.get(i));
            dfs(groups, idx+1, path); //recurse
            path.setLength(le); //backtrack
        }
    }
}
