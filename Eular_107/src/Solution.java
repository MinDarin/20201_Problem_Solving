//2015115907 Kimminseok :  Use Prim - PriorityQueue
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Solution {
	public static void main(String[] args)
	{
		run r = new run();
	}
}

class run {
	private String temp_line;
	private String[] split_line;
	
	private int edge_num;
	private int line_num;
	
	private int start_edge;
	private int end_edge;
	private int weight;
	private int[] head;
	linkedlist[] adjlist;
    run()
	{
 		this.fileinput();
		System.out.println(this.Prim());
	}
	private void fileinput()
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			temp_line = br.readLine();
			split_line = temp_line.split(" ");
			edge_num = Integer.parseInt(split_line[0]);
			line_num = Integer.parseInt(split_line[1]);
			adjlist = new linkedlist[edge_num+1];
			head = new int[edge_num+1];

			for(int i = 0; i <=edge_num; i++)
			{
			adjlist[i] = new linkedlist();
			head[i] = -1;
			}
			for(int i = 0; i <line_num; i++)
			{
			temp_line = br.readLine();
			split_line = temp_line.split(" ");
			start_edge = Integer.parseInt(split_line[0]);
			end_edge = Integer.parseInt(split_line[1]);
			weight = Integer.parseInt(split_line[2]);			
			node new_node = new node(start_edge, end_edge,weight);
			node reverse_node = new node(end_edge, start_edge,weight);
			adjlist[start_edge].add(new_node);
			adjlist[end_edge].add(reverse_node);
			}
		}
		catch(Exception e)
		{
		}
	}
	private int Prim()
	{
		int t_set_size = 0;
		int[] t_set = new int[edge_num+1];
		int min_start_edge = 0;
		int min_end_edge = 0;
		int min_weight = 100001;
		int total=0;
		t_set[0] = 1;
	    PriorityQueue<node> pq=new PriorityQueue<node>();
	    node insert_node = adjlist[1].head.link;
	    while(insert_node != null)
	    {
	    	pq.offer(insert_node);
	    	insert_node = insert_node.link;
	    }
		while(t_set_size <= edge_num - 1)
		{
			node temp_node = pq.poll();
			if(temp_node == null)
				break;
			if(head[temp_node.end_edge] == 1) //사이클처리
				continue;
			min_weight = temp_node.weight;
			min_start_edge = temp_node.start_edge;
			min_end_edge = temp_node.end_edge;				
			head[min_start_edge] = 1;
			head[min_end_edge] = 1;
		    total += min_weight;
			insert_node = adjlist[min_end_edge].head.link;
		    while(insert_node != null)
		    {
		    	pq.offer(insert_node);
		    	insert_node = insert_node.link;
		    }

		}
		return total;
	}
}

class linkedlist{
	node head = new node(0,0,0);
	public void add(node n)
	{
		if(head.link == null)
				this.head.link = n;
		else
		{
		node temp_link = head.link;
		boolean flag = true;
		while(true)
		{
			if(temp_link.end_edge == n.end_edge)	//멀티 패스 처리
			{
				if(temp_link.weight >= n.weight)
				{
					temp_link.weight = n.weight;
					flag = false;
					break;
				}
			}
			else if(temp_link.link == null)
				break;
			temp_link = temp_link.link;
		}
		if(flag)
		temp_link.link = n;
		}
	}
}

class node implements Comparable<node>{
	int start_edge = 0;
	int end_edge = 0;
	int weight = 0;
	node link = null;
	node(int s, int e, int w)
	{
		this.start_edge =s;
		this.end_edge = e;
		this.weight = w;
		this.link = null;
	}
	@Override
	public int compareTo(node n)
	{
		if(this.weight > n.weight)
			return 1;
		else if (this.weight < n.weight) return -1;
		return 0;
	}
}
