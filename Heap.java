package assignment;

import java.util.ArrayList;
import java.util.Comparator;

import assignment.CSE535Assignment.freqcompareheap;


public class Heap<T> {

	class Heapobject
	{
		String docid;
		Integer pointer;
	}
	
	public class heapdocidcompare implements Comparator<Heapobject>  
	{
						public int compare(Heapobject o1, Heapobject o2) {
				            return o1.docid.compareTo(o2.docid);
				        }
						
	}
	
	ArrayList<T> heap = new ArrayList<T>();
	int size=0;
	Comparator<T> c;
	
	public Heap()
	{
		
	}
	
	public Heap(Comparator<T> c1)
	{

		c = c1;
	}
	
	
	public ArrayList<T> Buildheap(ArrayList<T> A)
	{
		int n = A.size();
		size = n;
		int i;
		T aux = null;
		heap.add(aux);
		for(i=0;i<n;i++)
		{
			heap.add(A.get(i));
		}
		T add;
		i= (int)Math.ceil((n)/2);
		
		for(;i>=1;i--)
		{
			percolateDown(heap,i);
		}
		
		return heap;
	}
	
	
	public T deleteMin()
	{
		T tmp = heap.get(1);
		heap.set(1, heap.get(size));
		heap.remove(size);
		size--;		
		percolateDown(heap,1);
		return tmp;
	}
	
	public void add(T t)
	{
		heap.add(t);
		size++;
		percolateUp(heap,size);
	}
	
	
	
	
	public T getMin()
	{
			return heap.get(1);
	}
	
	public void percolateUp(ArrayList<T> A,int i)
	{
		
		int l,r,min,p;
		T tmp;
		p = (int)Math.floor((i)/2);
		min = i;
		if(i<1)
			return;
		if(c.compare(A.get(p), A.get(min)) > 0)
		{
			tmp = A.get(p);
			A.set(p, A.get(min));
			A.set(min,tmp);
			min = p;
			percolateUp(A,min);
		}
		
	}
	
	
	public void percolateDown(ArrayList<T> A, int i)
	{
		
		int l,r,min,n;
		T tmp;
		n = size;
		l = 2*i;
		r = 2*i+1;
		
		if(l>n && r>n)
			return;
		if(l<=n && c.compare(A.get(i), A.get(l)) > 0)
		{
			min = l;
		}
		else
		{
			min = i;
		}
		
		if(r<=n && c.compare(A.get(r), A.get(min)) < 0)
		{
			min = r;
		}
		
		if(min!=i)
		{
			tmp = A.get(min);
			A.set(min, A.get(i));
			A.set(i, tmp);
			percolateDown(A,min);
		}
		
		
	}
	
	
	public Heap createheap()
	{
		Heap obj = new Heap<Heapobject>(new heapdocidcompare());
		ArrayList<Heapobject> arr_new = new ArrayList<Heapobject>();
		
		Heapobject hob = new Heapobject();
		
		hob.docid = "1";
		hob.pointer = 1;
		arr_new.add(hob);
		
		hob = new Heapobject();		
		hob.docid = "6";
		hob.pointer = 1;
		arr_new.add(hob);
		
		hob = new Heapobject();		
		hob.docid = "5";
		hob.pointer = 1;
		arr_new.add(hob);
		
		hob = new Heapobject();		
		hob.docid = "8";
		hob.pointer = 1;
		arr_new.add(hob);
		
		hob = new Heapobject();		
		hob.docid = "3";
		hob.pointer = 1;
		arr_new.add(hob);
		
		hob = new Heapobject();		
		hob.docid = "2";
		hob.pointer = 1;
		arr_new.add(hob);
		
		ArrayList<Heapobject> res = new ArrayList<Heapobject>();
		
		
		
		
		res = obj.Buildheap(arr_new);
		
		
		
		int i;
		
		for(i=0;i<arr_new.size();i++)
			System.out.println(arr_new.get(i).docid+",");
		
		
		for(i=1;i<res.size();i++)
		{
			System.out.print(res.get(i).docid+",");
		}
		System.out.println();
		i=res.size();
		Heapobject u;
		while(i>1)
		{
			u = (Heapobject)obj.deleteMin();
			System.out.print(u.docid);
			i--;
		}
		
		return obj;
	}
	
	public static void main(String args[])
	{
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		arr.add(5);
		arr.add(10);
		arr.add(9);
		arr.add(15);
		arr.add(2);
		arr.add(1);
		
		
		Heap p  = new Heap();
		Heap obj = p.createheap();
		/*ArrayList<Integer> h = obj.Buildheap(arr); 
		int i;
		
		for(i=0;i<h.size();i++)
			System.out.print(h.get(i)+" ");
		System.out.println();
		System.out.println(obj.deleteMin());
		h = obj.heap;
		for(i=0;i<h.size();i++)
			System.out.print(h.get(i)+" ");
		System.out.println();
		obj.add(3);
		h = obj.heap;
		for(i=0;i<h.size();i++)
			System.out.print(h.get(i)+" ");*/
	}
	
	
	
	
	
}
