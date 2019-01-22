precision mediump float;

uniform vec4 u_emissiveColor;

varying vec3 v_lightSpecular;
varying vec3 v_lightDiffuse;

void main() {
	gl_FragColor = vec4(v_lightDiffuse + v_lightSpecular + u_emissiveColor.rgb, 1.0);
}
