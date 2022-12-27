package com.github.annasajkh.shapes;

import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;

public class Rectangle
{
    public VertexInfo vertex1;
    public VertexInfo vertex2;
    public VertexInfo vertex3;
    public VertexInfo vertex4;

    public Rectangle(VertexInfo vertex1, VertexInfo vertex2, VertexInfo vertex3, VertexInfo vertex4)
    {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        this.vertex4 = vertex4;
    }

    public void updateVertexInfo(VertexInfo vertex1, VertexInfo vertex2, VertexInfo vertex3, VertexInfo vertex4)
    {
        this.vertex1.set(vertex1);
        this.vertex2.set(vertex2);
        this.vertex3.set(vertex3);
        this.vertex4.set(vertex4);
    }
}
