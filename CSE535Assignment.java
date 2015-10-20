package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class CSE535Assignment {

	
	class Heapobject
	{
		String docid;
		Integer pointer;
	}
	
	class Heap {

		
		ArrayList<Integer> heap = new ArrayList<Integer>();
		int size=0;		
		public ArrayList<Integer> Buildheap(ArrayList<Integer> A)
		{
			int n = A.size();
			size = n;
			int i;
			heap.add(-1);
			for(i=0;i<n;i++)
			{
				heap.add(A.get(i));
			}
			i = (int)Math.ceil((n)/2);
			for(;i>=1;i--)
			{
				percolateDown(heap,i);
			}
			
			return heap;
		}
		
		
		public int deleteMin()
		{
			int tmp = heap.get(1);
			heap.set(1, heap.get(size));
			heap.remove(size);
			size--;		
			percolateDown(heap,1);
			return tmp;
		}
		
		public void add(int t)
		{
			heap.add(t);
			size++;
			percolateUp(heap,size);
		}
		
		
		
		
		public int getMin()
		{
				return heap.get(1);
		}
		
		public void percolateUp(ArrayList<Integer> A,int i)
		{
			
			int l,r,min,tmp,p;
			
			p = (int)Math.floor((i)/2);
			min = i;
			if(i<1)
				return;
			if(A.get(p)>A.get(min))
			{
				tmp = A.get(p);
				A.set(p, A.get(min));
				A.set(min,tmp);
				min = p;
				percolateUp(A,min);
			}
			
		}
		
		
		public void percolateDown(ArrayList<Integer> A, int i)
		{
			
			int l,r,min,n,tmp;
			
			n = size;
			l = 2*i;
			r = 2*i+1;
			
			if(l>n && r>n)
				return;
			if(l<=n && A.get(i)>A.get(l))
			{
				min = l;
			}
			else
			{
				min = i;
			}
			
			if(r<=n && A.get(r)<A.get(min))
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
	}
	
	
	class docidwithfreq
	{
		String docid;
		Integer freq;
	}
	
	class PostingObject
	{
		Integer termfreq;
		LinkedList<docidwithfreq> index_docId;
		LinkedList<docidwithfreq> index_termfreq;
	}
	
	class Result
	{
		Integer comp;
		LinkedList<docidwithfreq> result;
		
	}
	
	public class termcompare implements Comparator<String>  
	{
						public int compare(String o1, String o2) {
				            return term_index.get(o1).termfreq.compareTo(term_index.get(o2).termfreq);
				        }
						
	}
	
	public class docidcompare implements Comparator<docidwithfreq>  
	{
						public int compare(docidwithfreq o1, docidwithfreq o2) {
				            return o1.docid.compareTo(o2.docid);
				        }
						
	}
			
	public class freqcompare implements Comparator<docidwithfreq> 
	{		
				public int compare(docidwithfreq o1, docidwithfreq o2) {
				            return o2.freq.compareTo(o1.freq);
				}
	}
	
	public class freqcompareheap implements Comparator<docidwithfreq> 
	{		
				public int compare(docidwithfreq o1, docidwithfreq o2) {
				            return o1.freq.compareTo(o2.freq);
				}
	}

	static HashMap<String,PostingObject> term_index = new HashMap<String,PostingObject>();
	static HashMap<String,LinkedList<docidwithfreq> > index_docId = new HashMap<String,LinkedList<docidwithfreq> >();
	static HashMap<String,LinkedList<docidwithfreq> > index_termfreq = new HashMap<String,LinkedList<docidwithfreq> >();
	static PriorityQueue<docidwithfreq> topk;
	
	public String print_output(String function_name)
	{
		String tmp = null;
		if(function_name.equals("getPostings"))
		{
			
		}
		return tmp;
	}
	
	public void create_index(String index_file, int k)
	{
		String line = new String();
		
		URL url = getClass().getClassLoader().getResource(index_file);
		freqcompareheap comp = new freqcompareheap();
		topk = new PriorityQueue<docidwithfreq>(k,comp);
		try{
			FileReader fileReader = new FileReader(url.getPath());
			BufferedReader bufferedreader = new BufferedReader(fileReader);
			int first_break,second_break;
			LinkedList<String> aux_docId = new LinkedList<String>();
			int aux_tfreq;
			String aux_term;
			String[] aux_postingdocs;
			String temp;
			String auxdocid;
			Integer auxtfreq;
			int i,next,j=0,nterms,tmp;
//			docidwithfreq[] docidaux = new docidwithfreq[5]; 
			String[] tmp_docid;
			while((line=bufferedreader.readLine())!=null)
			{
				first_break = line.indexOf("\\");
				second_break = line.indexOf("\\", first_break+1);
				aux_term = line.substring(0, first_break);
				temp = line.substring(first_break+2, second_break);
				aux_tfreq = Integer.valueOf(temp);
				LinkedList<docidwithfreq> docidlist = new LinkedList<docidwithfreq>();
				PostingObject postOb = new PostingObject();
				postOb.termfreq = aux_tfreq;
			//	System.out.println(line.length());
				if(topk.size()<k)
				{
					docidwithfreq aux = new docidwithfreq();					
					aux.docid = aux_term;
					aux.freq = aux_tfreq;
					topk.add(aux);
				}
				else if(topk.peek().freq<aux_tfreq)
				{
					
						topk.poll();
						docidwithfreq aux = new docidwithfreq();					
						aux.docid = aux_term;
						aux.freq = aux_tfreq;
						topk.add(aux);
					
				}
				tmp_docid = line.substring(second_break+3, line.length()-1).split(",");
				
				nterms = tmp_docid.length;
				for(i=0;i<nterms;i++)
				{
					docidwithfreq docidaux = new docidwithfreq();
					tmp = tmp_docid[i].indexOf("/");
					auxdocid = tmp_docid[i].substring(0, tmp).trim();
					auxtfreq = Integer.valueOf(tmp_docid[i].substring(tmp+1).trim());
					docidaux.docid = auxdocid;
					docidaux.freq = auxtfreq;
					docidlist.add(docidaux);
				}
				
				Collections.sort(docidlist,new docidcompare());
				postOb.index_docId = new LinkedList<docidwithfreq>();
				postOb.index_docId.addAll(docidlist);
				Collections.sort(docidlist,new freqcompare());
				postOb.index_termfreq = new LinkedList<docidwithfreq>();
				postOb.index_termfreq.addAll(docidlist);
				term_index.put(aux_term, postOb);
	//			index_termfreq.put(aux_term, docidlist);
			}
			
			bufferedreader.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	
	public Result termAtATimeAnd(String[] terms)
	{
		
		LinkedList<docidwithfreq> res = new LinkedList<docidwithfreq>();		
		LinkedList<docidwithfreq> aux = new LinkedList<docidwithfreq>();
		LinkedList<docidwithfreq> aux2 = new LinkedList<docidwithfreq>();
		
		int i,m,n,s1,s2,comp=0;
		Result final_res = new Result();
		
		for(i=0;i<terms.length;i++)
		{
			if(!term_index.containsKey(terms[i]))
			{
				final_res.comp = 0;
				final_res.result = null;
				return final_res;
			}
		}
		
		res.addAll(term_index.get(terms[0]).index_termfreq);

		for(i=1;i<terms.length;i++)
		{
			aux.clear();
			aux.addAll(res);
			res.clear();
			aux2.clear();
			aux2.addAll(term_index.get(terms[i]).index_termfreq);
			s1 = aux.size();
			s2 = aux2.size();
			m = 0;
			n = 0;
			while(m<s1)
			{
				n = 0;
				while(n<s2)
				{
					comp++;
					if(aux.get(m).docid.equals(aux2.get(n).docid))
					{
						res.add(aux.get(m));
						break;
					}
					n++;
				}
				m++;
			}
		}
		
		Collections.sort(res, new docidcompare());

		final_res.comp = comp;
		final_res.result = res;
		return final_res;
	}
	
	public Result termAtATimeOr(String[] terms)
	{
		LinkedList<docidwithfreq> res = new LinkedList<docidwithfreq>();
		
		int i,m,n,s1,s2;
		LinkedList<docidwithfreq> aux = new LinkedList<docidwithfreq>();
		LinkedList<docidwithfreq> aux2 = new LinkedList<docidwithfreq>();
		
		ArrayList<String> terms_list = new ArrayList<String>(Arrays.asList(terms));
		Result final_res = new Result();
		
		
		for(i=0;i<terms_list.size();i++)
		{
			if(!term_index.containsKey(terms_list.get(i)))
			{
				terms_list.remove(i);
			}
		}
		
		if(terms_list.size()==0)
		{
			final_res.comp = 0;
			final_res.result = null;
			return final_res;
		}
		
		
		
		int comp = 0;
		res.addAll(term_index.get(terms[0]).index_termfreq);
		comp = comp + res.size();
		boolean flag = true;
		for(i=1;i<terms.length;i++)
		{
			aux2 = term_index.get(terms[i]).index_termfreq;
			m = 0;
			n = 0;
			s1 = res.size();
			s2 = aux2.size();
			
			while(n<s2)
			{
				
				m = 0;
				flag = true;
				while(m<s1)
				{
					comp++;
					if( res.get(m).docid.compareTo(aux2.get(n).docid) == 0)
					{
						flag = false;
						break;
					}
					m++;
				}
				if(flag)
				{
					res.add(aux2.get(n));
				}
				n++;
			}
		}
		Collections.sort(res, new docidcompare());

		final_res.comp = comp;
		final_res.result = res;
		return final_res;
		
		
		
	}
	
	public int findmax(ArrayList<Integer> pointers,String terms[])
	{
		int res = 0;
		int i,s = pointers.size();
		String tmp = term_index.get(terms[0]).index_docId.get(pointers.get(0)).docid;
		for(i=0;i<s;i++)
		{
			if(tmp.compareTo(term_index.get(terms[i]).index_docId.get(pointers.get(i)).docid)<0)
			{
				res = i;
				tmp = term_index.get(terms[i]).index_docId.get(pointers.get(i)).docid;
			}
		}
		
		return res;
		
		
	}
	
	
	public Result documentAtATimeAnd(String[] terms)
	{
		

		LinkedList<docidwithfreq> res = new LinkedList<docidwithfreq>();
		
		LinkedList<docidwithfreq> aux = new LinkedList<docidwithfreq>();
		
		Result final_res = new Result();
		int i,nterms = terms.length;
		
		for(i=0;i<terms.length;i++)
		{
			if(!term_index.containsKey(terms[i]))
			{
				final_res.comp = 0;
				final_res.result = null;
				return final_res;
			}
		}
		
		
		ArrayList<Integer> pointers = new ArrayList<Integer>(terms.length);
		
		
		for(i=0;i<nterms;i++)
			pointers.add(0);
		Integer max = 0;
		String maxstring = term_index.get(terms[0]).index_docId.get(pointers.get(0)).docid;
		String tmp;
		int count = 0;
		boolean flag = false;
		int comp = 0;
		while(true)
		{
			max = findmax(pointers,terms);
			comp = comp + pointers.size();
			maxstring = term_index.get(terms[max]).index_docId.get(pointers.get(max)).docid;
			count = 0;
			flag = false;
			for(i=0;i<nterms;i++)
			{
				
				aux = term_index.get(terms[i]).index_docId;
				comp++;
				if(pointers.get(i).compareTo(aux.size())<0)
				{
					
					tmp = term_index.get(terms[i]).index_docId.get(pointers.get(i)).docid;
					if(maxstring.equals(tmp))
					{
						count++;
					}
					else
					{
						pointers.set(i, pointers.get(i)+1);
						if(pointers.get(i).compareTo(aux.size())>=0)
						{	
							flag = true;
				//			break;
						}
					}
				}
				else
				{
					flag = true;
					break;
				}
				
			}
			
			if(count == nterms)
			{
				res.add(term_index.get(terms[max]).index_docId.get(pointers.get(max)));
				
				for(i=0;i<nterms;i++)
				{
					pointers.set(i, pointers.get(i)+1);
					aux = term_index.get(terms[i]).index_docId;
					comp++;
					if(pointers.get(i).compareTo(aux.size())>=0)
					{
						flag = true;
						break;
					}
				}
			}
			if(flag)
			{
				break;
			}
		}
		
		
		final_res.comp = comp;
		final_res.result = res;
		
		
		return final_res;

	}
	

	public int findmin(ArrayList<Integer> pointers,ArrayList<String> terms)
	{
		int res = 0;
		int i,s = pointers.size();
		String tmp = term_index.get(terms.get(0)).index_docId.get(pointers.get(0)).docid;
		for(i=0;i<s;i++)
		{
			if(tmp.compareTo(term_index.get(terms.get(i)).index_docId.get(pointers.get(i)).docid)>0)
			{
				res = i;
				tmp = term_index.get(terms.get(i)).index_docId.get(pointers.get(i)).docid;
			}
		}
		
		return res;				
	}
	
	
	
	public Result documentAtATimeOr(String[] terms)
	{

		LinkedList<docidwithfreq> res = new LinkedList<docidwithfreq>();
		
		LinkedList<docidwithfreq> aux = new LinkedList<docidwithfreq>();
		
		Result final_res = new Result(); 
		int i,nterms = terms.length;
		ArrayList<String> terms_list = new ArrayList<String>(Arrays.asList(terms));
		
		for(i=0;i<terms_list.size();i++)
		{
			if(!term_index.containsKey(terms_list.get(i)))
			{
				terms_list.remove(i);
			}
		}
		
		if(terms_list.size()==0)
		{
			final_res.comp = 0;
			final_res.result = null;
			return final_res;
		}
		
		ArrayList<Integer> pointers = new ArrayList<Integer>(terms_list.size());
		
		
		for(i=0;i<nterms;i++)
			pointers.add(0);
		Integer min = 0;		
		String minstring = term_index.get(terms_list.get(0)).index_docId.get(pointers.get(0)).docid;
		String tmp;
		int count = 0;
		boolean flag = false;
		int comp = 0;
		while(nterms>0)
		{
			
			min = findmin(pointers,terms_list);
			comp = comp + pointers.size();
			minstring = term_index.get(terms_list.get(min)).index_docId.get(pointers.get(min)).docid;
			count = 0;
			res.add(term_index.get(terms_list.get(min)).index_docId.get(pointers.get(min)));
			for(i=0;i<pointers.size();i++)
			{
				aux = term_index.get(terms_list.get(i)).index_docId;
				comp++;
				if(pointers.get(i).compareTo(aux.size())<0)
				{
					tmp = term_index.get(terms_list.get(i)).index_docId.get(pointers.get(i)).docid;
					if(minstring.equals(tmp))
					{
						pointers.set(i, pointers.get(i)+1);
						if(pointers.get(i).compareTo(aux.size())>=0)
						{	
							pointers.remove(i);
							terms_list.remove(i);
							i--;
						}
						count++;
					}
					
				}
				else
				{
					pointers.remove(i);
					terms_list.remove(i);
					i--;
				}
				
			}
			/*if(count == pointers.size())
			{
				res.add(term_index.get(terms[min]).index_docId.get(pointers.get(min)));
				
				for(i=0;i<nterms;i++)
				{
					pointers.set(i, pointers.get(i)+1);
					aux = term_index.get(terms[i]).index_docId;
					comp++;
					if(pointers.get(i).compareTo(aux.size())<0)
					{
						pointers.remove(i);
						terms_list.remove(i);
					}
				}
			}*/
			
			nterms = terms_list.size();
		}
				
		final_res.comp = comp;
		final_res.result = res;		
		return final_res;
	}
	
	
	public void runqueries(String queryfile,int k)
	{
		String line = new String();

		URL url = getClass().getClassLoader().getResource(queryfile);
		try{
			FileReader fileReader = new FileReader(url.getPath());
			BufferedReader bufferedreader = new BufferedReader(fileReader);
			String[] aux_postingdocs,terms,sorted_terms;
			String temp;
			String auxdocid;
			Integer auxtfreq;
			int i,next,j=0;
	
			PrintWriter writer = new PrintWriter("output.txt","UTF-8");			
			writer.println("FUNCTION: getTopK "+k);
			writer.print("Result: ");
			String[] res = new String[k];			
			for(i=0;i<k;i++)
			{
				res[k-i-1] = topk.poll().docid;
			}
			for(i=0;i<k-1;i++)
			{
				writer.print(res[i]+",");
			}
			writer.print(res[i]);
			writer.println();
			LinkedList<docidwithfreq> result = new LinkedList<docidwithfreq>();
			ArrayList<Integer> valid_place = new ArrayList<Integer>();
			long startTime,endTime;
			int s;
			Result out;
			boolean flag_single = false,flag_all = false;
			while((line=bufferedreader.readLine())!=null)
			{
				flag_single = false;
				flag_all = true;
				valid_place = new ArrayList<Integer>();
				if(line.trim().isEmpty())
					break;
				terms = line.split(" ");
				sorted_terms = Arrays.copyOf(terms, terms.length);
				
				//Printing postings
				for(j=0;j<terms.length;j++)
				{
					
					writer.println("FUNCTION: getPostings "+terms[j]+", ");
					writer.print("Ordered by doc IDs: ");
					
					if(!term_index.containsKey(terms[j]))
					{
						writer.println("Term not found in index");
						writer.print("Ordered by TF: ");
						writer.println("Term not found in index");
						flag_single = true;
						continue;
					}
					
					flag_all = false;
					valid_place.add(j);
					LinkedList<docidwithfreq> post = term_index.get(terms[j]).index_docId;
					s = post.size();
					
					for(i=0;i<s-1;i++)
					{
						writer.print(post.get(i).docid+", ");
					}					
					writer.println(post.get(i).docid);

					writer.print("Ordered by TF: ");		
					post = term_index.get(terms[j]).index_termfreq;
					s = post.size();
					for(i=0;i<s-1;i++)
					{
						writer.print(post.get(i).docid+", ");
					}					
					writer.print(post.get(i).docid);
					writer.println();
				}
				
				//termAtATimeQueryAnd Starting						
				writer.print("FUNCTION: termAtATimeQueryAnd ");
				for(i=0;i<terms.length-1;i++)
					writer.print(terms[i]+", ");
				writer.println(terms[i]);
				startTime = System.currentTimeMillis();
				out = termAtATimeAnd(terms);				
				endTime = System.currentTimeMillis();
				result = out.result;
				if(result.size()==0)
				{
					writer.println(0+" documents are found");
				}
				else
				{
					writer.println(result.size()+" documents are found");
				}
				writer.println(out.comp+" comparisions are made");
				writer.println((endTime-startTime)/1000+" seconds are used");
				
				//optimized termATATimeAnd by sorting the terms based on frequency
				Arrays.sort(sorted_terms,new termcompare());
				for(i=0;i<sorted_terms.length;i++)
					System.out.print(sorted_terms[i]+" "+term_index.get(sorted_terms[i]).termfreq);
				out = termAtATimeAnd(sorted_terms);
				writer.println(out.comp+" comparisions are made with optimization");
				writer.print("Result: ");
				if(result.size()>0)
				{
					for(i=0;i<result.size()-1;i++)
					{
						writer.print(result.get(i).docid+", ");
					}
					writer.println(result.get(i).docid);
				}
				else
					writer.println("0 documents found");
				
				//termAtATimeQueryOr Starting				
				writer.print("FUNCTION: termAtATimeQueryOr ");
				for(i=0;i<terms.length-1;i++)
					writer.print(terms[i]+", ");
				writer.println(terms[i]);
				startTime = System.currentTimeMillis();
				out = termAtATimeOr(terms);				
				endTime = System.currentTimeMillis();
				result = out.result;
				if(result.size()==0)
				{
					writer.println(0+" documents are found");
				}
				else
				{
					writer.println(result.size()+" documents are found");
				}
				writer.println(out.comp+" comparisions are made");
				writer.println((endTime-startTime)/1000+" seconds are used");
				
				//optimized termATATimeOr by sorting the terms based on frequency
				out = termAtATimeOr(sorted_terms);
				writer.println(out.comp+" comparisions are made with optimization");
				writer.print("Result: ");
				
				if(result.size()>0)
				{
					for(i=0;i<result.size()-1;i++)
					{
						writer.print(result.get(i).docid+", ");
					}
					writer.println(result.get(i).docid);
				}
				else
					writer.println("0 documents found");
				
				//documentAtATimeQueryAnd Starting				
				writer.print("FUNCTION: documentAtATimeQueryAnd ");
				for(i=0;i<terms.length-1;i++)
					writer.print(terms[i]+", ");
				writer.println(terms[i]);
				startTime = System.currentTimeMillis();
				out = documentAtATimeAnd(terms);				
				endTime = System.currentTimeMillis();
				result = out.result;
				if(result.size()==0)
				{
					writer.println(0+" documents are found");
				}
				else
				{
					writer.println(result.size()+" documents are found");
				}
				writer.println(out.comp+" comparisions are made");
				writer.println((endTime-startTime)/1000+" seconds are used");
				
				writer.print("Result: ");
				if(result.size()>0)
				{	
					for(i=0;i<result.size()-1;i++)
					{
						writer.print(result.get(i).docid+", ");
					}
					writer.println(result.get(i).docid);
				}
				else
					writer.println("0 documents found");
				
				//documentAtATimeQueryOr Starting				
				writer.print("FUNCTION: documentAtATimeQueryOr ");
				for(i=0;i<terms.length-1;i++)
					writer.print(terms[i]+", ");
				writer.println(terms[i]);
				startTime = System.currentTimeMillis();
				out = documentAtATimeOr(terms);				
				endTime = System.currentTimeMillis();
				result = out.result;
				writer.println(result.size()+" documents are found");
				writer.println(out.comp+" comparisions are made");
				writer.println((endTime-startTime)/1000+" seconds are used");
				
				writer.print("Result: ");
				if(result.size()>0)
				{	
					for(i=0;i<result.size()-1;i++)
					{
						writer.print(result.get(i).docid+", ");
					}
					writer.println(result.get(i).docid);
				}
				else
					writer.println("0 documents found");
				
			}
			writer.close();
			bufferedreader.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
		}	
	}
	
	public void check(LinkedList<docidwithfreq> res)
	{
		int i;
		int l = res.size();
		
		for(i=0;i<l-1;i++)
		{
			if(res.get(i).docid.equals(res.get(i+1).docid))
			{
				
				System.out.println("duplicate "+res.get(i).docid);
			}
		}
		
		
	}
	
	
	public static void main(String args[])
	{
		
		CSE535Assignment obj = new CSE535Assignment();
		
		String index_file = args[0];
		String output_log = args[1];
		int k = Integer.valueOf(args[2]);
		String query_file = args[3];
		obj.create_index(index_file,k);
		obj.runqueries(query_file,k);
		
	}
	
	
}
