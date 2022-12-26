#ifdef GL_ES
precision mediump float;
#endif



varying vec4 vColor;
varying vec3 vNormal;
varying vec3 vFragPos;
varying vec3 vCameraDir;

void main() {
	vec3 vLightDir = vec3(0.0, 1.0, 0.0);

	float ambientStrength = 0.8;

	vec3 ambient = ambientStrength * vec3(1.0, 1.0, 1.0);


	vec3 lightDir = normalize(vLightDir - vFragPos);

	float diffuseLight = max(dot(vNormal, lightDir), 0.1);

    gl_FragColor = vec4(vec3(vColor.xyz), 1.0); //* diffuseLight * ambient, 1.0);
}
