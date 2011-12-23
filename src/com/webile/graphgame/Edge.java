package com.webile.graphgame;
public class Edge
{
	char chIdentifier;
	Vertex V1;
	Vertex V2;
	boolean bState; //true implies shorted or marked,false unmarked!
	Edge next;
	int xa,xb,ya,yb,distance;
	final static int rangeArea=5;
//constructor.
	Edge(Edge E,char id,Vertex HeadVertex,int vertex1,int vertex2)
	{
		next=E;
		chIdentifier=id;
		bState=false;

		V1=HeadVertex.searchVertexList(vertex1);
		if(V1==null)
		{
			V2=null;
			System.out.print("Error ,Vertex not found!");
			return;
		}
		V2=HeadVertex.searchVertexList(vertex2);
		if(V2==null)
		{
			V1=null;
			System.out.print("Error ,Vertex not found!");
			return;
		}
		V1.addRelative(V2,this);
		V2.addRelative(V1,this);
		xa=V1.iPosx;
		ya=V1.iPosy;
		xb=V2.iPosx;
		yb=V2.iPosy;
		distance=(int)Math.sqrt((double)((xa-xb)*(xa-xb)+(ya-yb)*(ya-yb)));
	}

	String display()
	{
		return chIdentifier+" "+V1.identifier+" to "+V2.identifier;
	}
	String displayAll()
	{
		if(next==null)
			return display();

		return display()+"\n"+next.displayAll();
	}
	Edge removeEdge(Edge E) //cut's play
	{
		if(this==E)
		{
			if(bState==true)
			{
				System.out.println("fatal error! deleting a permanent edge");
				System.exit(0);
			}
			V1.removeRelative(V2);
			V2.removeRelative(V1);
			return next;
		}

		if(next!=null)
			this.next = next.removeEdge(E);
		return this;
	}
	boolean markEdge()  //short's play
	{
		bState=true;
		V1.markAsPermanent(V2);
		V2.markAsPermanent(V1);
		return true;
	}
	Edge searchEdgeList(char ch)
	{
		if(ch==chIdentifier)
			return this;
		if(next==null)
			return null;
		return next.searchEdgeList(ch);
	}

	Edge IsInRange(int px,int py)
	{
		if(checkArea(px,py)==true)
			return this;
		if(next!=null)
			return next.IsInRange(px,py);
		return null;
	}
	boolean checkArea(int x,int y)
	{
		int iDistanceY,iDistanceX;

		iDistanceY=Math.abs((xa-xb)*y-(ya-yb)*x-xa*yb+ya*xb)/distance;

	iDistanceX=Math.abs(y*(yb-ya)+x*(xb-xa)-(yb*yb-ya*ya+xb*xb-xa*xa)/2)/distance;

		if(iDistanceY<=rangeArea&&iDistanceX<=distance/2)
			return true;
		return false;
	}
	int caluculateInDegree()
	{
		return V1.inDegree+V2.inDegree;
	}
}