attribute vec3 a_position;
attribute vec4 a_color;
attribute vec3 a_normal;

uniform mat4 u_projection;

//send to fragment shader
varying vec4 vColor;
varying vec3 vNormal;
varying vec3 vFragPos;

void main() {
    vColor = a_color;
    vNormal = a_normal;

    vFragPos = a_position;

    gl_Position = u_projection * vec4(a_position, 1.0);
}
