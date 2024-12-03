#version 400 core

in vec2 fragTextureCoord;        // Input color from vertex shader
out vec4 fragColour;   // Final color output
uniform sampler2D textureSampler;
void main() {
    fragColour = texture(textureSampler,fragTextureCoord);
}
