attribute vec2 vPosition;
	varying vec2 texCoord;
	void main()
	{
		gl_Position = vec4(vPosition, 0.0, 1.0);
		//An opportunism code. Do not use it unless you know what it means.
		texCoord = (vPosition.xy + 1.0) / 2.0;
	}