package com.webile.graphgame;
class Vertex
{
	 // Attributes.
	int identifier;
	int iPosx;
	int iPosy;
	static final int MAX=1000;
	Relatives relHead;
	Vertex next;
	int inDegree;
	int markedInDegree;
	//traversal attributes
	int tDistance;
	Vertex tParent;
	//special traversal
	int sDistance;
	Vertex sParent;
//constructor.
	Vertex(Vertex V,int id,int x,int y)
	{
		identifier = id;
		iPosx = x+10;
		iPosy = y+80;
		relHead = null;
		inDegree = 0;
		markedInDegree = 0;
		next = V;
		tDistance = MAX;
		tParent = null;
	}
//functions for maintaining the Vertices List.
	Vertex removeVertex(int id)
	{
		// this function is not needed
		if(identifier==id)
			return this.next;
		if(next!=null)
			this.next = next.removeVertex(id);
		return this;
	}
	String displayAll()
	{
		String output=new String(identifier+"\n");
		if(next!=null)
			return output+next.displayAll();
		return output;
	}
	Vertex searchVertexList(int id)
	{
		if(id==identifier)
			return this;
		if(next!=null)
			return next.searchVertexList(id);
		return null;
	}
//functions 4 relatives
	String displayRelatives()
	{
		if (relHead==null)
			return "\nVertex "+identifier+" has no relatives!";
		return "\nvertex "+identifier+"relatives are"+relHead.displayAllRelatives();
	}
	void removeRelative(Vertex V)
	{
		relHead=relHead.remove(V);
		inDegree--;
	}
	boolean checkRelative(Vertex V)
	{
		if((relHead.findEdge(V))!=null)return true;
		return false;
	}
	void addRelative(Vertex V,Edge E)
	{
		relHead=new Relatives(relHead,V,E);
		inDegree++;
	}
	void markAsPermanent(Vertex V)
	{
		relHead.mark(V);
		markedInDegree++;
	}
// functions 4 traversal
	void cutTraversal(Vertex parentV )
	{
		if (parentV==null)
	    {
			tDistance=0;
			tParent=null;
		}
		else
		{
			if(tDistance<=(parentV.tDistance+1))
				return;
			tParent= parentV;
			tDistance=tParent.tDistance+1;
		}
		Relatives temp=relHead;
		while(temp!=null)
		{
			temp.name.cutTraversal(this);
			temp=temp.next;
		}
		return;
	}
	void shortTraversal(Vertex parentV)
	{
		if (parentV==null)
	    {
			tDistance=0;
			tParent=null;
		}
		else
		{
			if(tDistance<=(parentV.tDistance+1))
				return;
		 	tParent= parentV;
			tDistance=tParent.tDistance+1;
		}
		Relatives temp=relHead;
		while(temp!=null)
		{
			if(temp.marked==true)
				temp.name.shortTraversal(this);
			temp=temp.next;
		}
		return;
	}
	void gameTraversal( Vertex parentV )
	{
		if (parentV==null)
	    {
			tDistance=0;
			tParent=null;
		}
		else
		{
			if(tDistance<=(parentV.tDistance+1))
				return;
			tParent= parentV;
			tDistance=tParent.tDistance;
			if( (relHead.findEdge(parentV).bState) != true )
				tDistance++;
		}
		Relatives temp=relHead;
		while(temp!=null)
		{
			temp.name.gameTraversal(this);
			temp=temp.next;
		}
		return;
	}

	void shortestPathTraversal(Vertex V )
	{
		if (V == null)
	    {
			sDistance=0;
			sParent=null;
		}
		else
		{
			if(sDistance<=(V.sDistance+1))
				return;
			sParent= V;
			sDistance=V.sDistance+1;
		}
		Relatives temp = relHead;
		while(temp!=null)
		{
			temp.name.shortestPathTraversal(this);
			temp=temp.next;
		}
		return;
	}

	void sRetrieval()
	{
		sParent=null;
		sDistance=MAX;
		if(this.next!=null)
			this.next.sRetrieval();
		return;
	}

	void retrieval()
	{
		tParent=null;
		tDistance=MAX;
		if(this.next!=null)
			this.next.retrieval();
		return;
	}

// functions for edges
	Edge edgeLeastInDegree()
	{
		if( tParent == null)
			return null;

		Edge tempEdge= relHead.findEdge(tParent);
		Edge nextEdge;
		nextEdge = tParent.edgeLeastInDegree();
		if(tempEdge.bState == true)
			return nextEdge;
		int tempInDegree = tempEdge.caluculateInDegree();

		if(nextEdge==null)
			return tempEdge;

		if( tempInDegree<nextEdge.caluculateInDegree() )
			return tempEdge;
		return nextEdge;
	}

	Edge findMustEdge()
	{
		if(tParent == null)
		return null;

		Edge tempEdge = relHead.findEdge(tParent);
		if(tempEdge.bState == false)
		{
			sRetrieval();
			removeRelative(tParent);
			//tParent.removeRelative(this);
			this.shortestPathTraversal(null);
			addRelative(tParent,tempEdge);
			if(tParent.sDistance == MAX)
				return tempEdge;
		}
		return tParent.findMustEdge();
	}
}// end of class vertex.