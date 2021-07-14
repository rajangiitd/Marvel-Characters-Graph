import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class pair{
    int f,s;
    public pair(int a,int b){
        this.f=a;
        this.s=b; }
}
class pair2{
    long f;
    String s;
    public pair2(long a,String b){
        this.f=a;
        this.s=b;
    }
}
class A4_2018ME20710{
    //return true if A<B
    public static boolean compare(pair2 A,pair2 B){
        if(A.f!=B.f) return A.f<B.f;
        int l1= A.s.length();
        int l2= B.s.length();
        int lmin= l1;
        if(l2<l1) lmin=l2;
        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)A.s.charAt(i);
            int str2_ch = (int)B.s.charAt(i);
            if (str1_ch != str2_ch) {
                return str1_ch<str2_ch;
            }
        }
        return l1<l2;
    }
    //merge array of pair2s
    public static void merge(pair2[] arr, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
        pair2[] L = new pair2[n1]; //temporary arrays for subarrays
        pair2[] R = new pair2[n2];
        //copy data
        for (int i = 0; i < n1; ++i) L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j) R[j] = arr[m + 1 + j];
        //merging
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (!compare(L[i],R[j])){
                arr[k] = L[i];
                i++;}
            else{
                arr[k] = R[j];
                j++;}
            k++;
        }
        //final-check
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    // Main function that sorts arr[l..r] using
    public static void pair2sort(pair2[] arr, int l, int r)
    {
        if (l < r){
            int m = (l + r) / 2; // Find middle point
            pair2sort(arr, l, m);     // Sort halves
            pair2sort(arr, m + 1, r);
            merge(arr, l, m, r); // Merge halves
        }
    }
    public static void mergeArrayList(ArrayList<pair2> arr, int l, int m, int r)
    {
        int n1 = m - l + 1; // Find sizes of two subarrays to be merged
        int n2 = r - m;
        pair2[] L = new pair2[n1]; /* Create temp arrays */
        pair2[] R = new pair2[n2];
        for (int i = 0; i < n1; ++i) L[i] = arr.get(l + i); /*Copy data to temp arrays*/
        for (int j = 0; j < n2; ++j) R[j] = arr.get(m + 1 + j);
        // Merge
        int i = 0, j = 0;
        int k = l; // Initial index of merged subarry array
        while (i < n1 && j < n2) {
            if (!compare(L[i],R[j])){
                arr.set(k,L[i]);
                i++;}
            else{
                arr.set(k,R[j]);
                j++;}
            k++;
        }
        //final-check
        while (i < n1) {
            arr.set(k,L[i]);
            i++;
            k++;
        }
        while (j < n2) {
            arr.set(k,R[j]);
            j++;
            k++;
        }
    }
    public static void pair2sortArrayList(ArrayList<pair2> arr, int l, int r)
    {
        if (l < r){
            int m = (l + r) / 2; // Find middle point
            pair2sortArrayList(arr, l, m);     // Sort halves
            pair2sortArrayList(arr, m + 1, r);
            mergeArrayList(arr, l, m, r); // Merge halves
        }
    }


    public static void dfs(int source,int[] visited,ArrayList<pair>[] Adj,int c_num){
        visited[source]=c_num;
        for (pair pp:Adj[source]) {
            if(visited[pp.f]==0){
                dfs(pp.f,visited,Adj,c_num);
            }
        }
    }

    public static void main(String[] args) throws IOException  {
        //long time = System.nanoTime();
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        HashMap<String,Integer> Nodestoi = new HashMap<>();
        HashMap<Integer,String> Nodeitos = new HashMap<>();
        String line= br.readLine();
        int i=0;
        String name;
        while ((line = br.readLine()) != null) {
            if(line.charAt(0)!='\"') name=line.substring((line.length()/2) +1,line.length());
            else name= line.substring((line.length()/2 )+2,line.length()-1 );
            if(Nodestoi.get(name)==null){
               i++;
               Nodeitos.put(i,name);
               Nodestoi.put(name,i);
            }
        }
        int n=i; //number of nodes
        int m=0; //number of edges
        ArrayList<pair>[] Adj = new ArrayList[n+1];
        for(int k=0;k<=n;k++){
            Adj[k] = new ArrayList<pair>();
        }
        BufferedReader br1 = new BufferedReader(new FileReader(args[1]));
        String line2= br1.readLine();
        int L,w,first_comma,sec_comma;
        String u,v;
        while((line2= br1.readLine())!=null){
            L=line2.length();
            sec_comma=L-1;
            while(true){
                if(line2.charAt(sec_comma)!=',') sec_comma--;
                else break;
            }
            first_comma=0;
            if(line2.charAt(0)=='\"'){
                first_comma++;
                while (line2.charAt(first_comma)!='\"') first_comma++;
                first_comma++;
            }else{
                while(line2.charAt(first_comma)!=',') first_comma++;
            }
            if(line2.charAt(0)=='\"') u = line2.substring(1,first_comma-1);
            else u= line2.substring(0,first_comma);

            if(line2.charAt(first_comma+1)=='\"') v= line2.substring(first_comma+2,sec_comma-1);
            else v= line2.substring(first_comma+1,sec_comma);
            w=Integer.parseInt(line2.substring(sec_comma+1, L));
            if(w>0){
                Adj[Nodestoi.get(u)].add(new pair(Nodestoi.get(v),w));
                Adj[Nodestoi.get(v)].add(new pair(Nodestoi.get(u),w));
                m++;
            }
        }
        if(args[2].equals("average")){
            if(n==0) System.out.print("0.00\n");
            else{
                System.out.printf("%.2f\n", (2.0*m) /(n));
            }
        }else if(args[2].equals("rank")){
            pair2[] to_sort = new pair2[n];
            for(int op=0;op<n;op++) to_sort[op]=new pair2(0,"");
            for(int uu=1;uu<=n;uu++){
                long co_occur=0;
                for(pair vv: Adj[uu]){
                    co_occur+=vv.s;
                }
                to_sort[uu-1]=new pair2(co_occur, Nodeitos.get(uu));
            }
            pair2sort(to_sort,0,n-1);
            if(n>0) {
                StringBuilder myanswer = new StringBuilder(to_sort[0].s);
                for (int ii = 1; ii < n; ii++) {
                    myanswer.append(",").append(to_sort[ii].s);
                }
                myanswer.append("\n");
                System.out.print(myanswer.toString());
            }else{
                System.out.print("\n");
            }
        }else if(args[2].equals("independent_storylines_dfs")){
            int[] visited = new int[n+1];
            int start=1;
            int component_number=1;
            while(start<=n){
                if(visited[start]!=0) start++;
                else{
                    dfs(start,visited,Adj,component_number);
                    component_number++;
                    start++;
                }
            }
            component_number--;
            int[] character_in_each_component = new int[component_number+1];
            for(int b=1;b<=n;b++){
                character_in_each_component[visited[b]]++;
            }
            /// components: 1 to component_number exists
            ArrayList<pair2>[] components = new ArrayList[component_number+1];
            for(int k=0;k<=component_number;k++){
                components[k]=new ArrayList<pair2>();
            }
            for(int y=1;y<=n;y++){
                components[visited[y]].add(  new pair2(0, Nodeitos.get(y))  );
            }
            for(ArrayList<pair2> mynodes: components){
                pair2sortArrayList(mynodes,0,mynodes.size()-1);
            }

            ArrayList<pair2> size_string = new ArrayList<>();

            HashMap<pair2,Integer> Final_map= new HashMap<>();
            ArrayList<pair2> Final_list = new ArrayList<>();
            for(int p=1;p<=component_number;p++){
                int size_component = character_in_each_component[p];
                //ArrayList<pair2> sorted_one = components[p];
                pair2 deciding = new pair2(size_component,components[p].get(0).s);
                Final_map.put(deciding,p);
                Final_list.add(deciding);
            }
            pair2sortArrayList(Final_list,0,Final_list.size()-1);
            StringBuilder answer= new StringBuilder("");
            for(pair2 group:Final_list){
                int p= Final_map.get(group);
                ArrayList<pair2> sorted_one = components[p];
                StringBuilder row = new StringBuilder(sorted_one.get(0).s);
                for(int index=1;index<sorted_one.size();index++){
                    row.append(",").append(sorted_one.get(index).s);
                }
                answer.append(row).append("\n");
            }
            if(n==0) System.out.print("\n");
            else System.out.print(answer.toString());
        }
        //System.err.println((System.nanoTime() - time) / 1e9);

    }
}
