package com.carl.views.rating;

/**
 * @author xiongke
 * @date 2021/10/22
 */
class VertexF {
    public VertexF() {
    }

    public VertexF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x;
    public float y;

    public VertexF next;
}