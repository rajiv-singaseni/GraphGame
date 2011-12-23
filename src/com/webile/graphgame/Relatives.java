package com.webile.graphgame;
class Relatives
{
	Vertex name;
	boolean marked;
	Relatives next;
	Edge edge;
	public Relatives(Relatives R,Vertex V,Edge E)
	{
		name=V;
		marked=false;
		edge=E;
		next=R;
	}

	boolean mark(Vertex V)
	{
		if (name==V)
		{
			marked=true;
			return true;
		}
		if (next!=null)
			return next.mark(V);
		return false;
	}
	Relatives remove(Vertex V)
	{
		if(name==V)
			return this.next;
		if(next!=null)
			this.next = next.remove(V);
		return this;
	}
	String displayAllRelatives()
	{
		String output=new String("  relative "+name.identifier+" marked "+marked+"of edge "+edge.chIdentifier);
		if(next!=null)
			return output+next.displayAllRelatives();
		return output;
	}
	Edge findEdge(Vertex V)
	{
		if (name==V)
		return edge;
		if ( next!=null)
			return next.findEdge(V);
		return null;
	}
}