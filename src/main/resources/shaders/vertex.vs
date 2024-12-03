#version 400 core

in vec3 position; // Input vertex position
in vec2 textureCoord;  // Output color to fragment shader

out vec2 fragTextureCoord;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main() {
    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0);
    fragTextureCoord= textureCoord;
}