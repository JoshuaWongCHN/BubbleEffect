package com.joshua.bubble;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage;

public class BubbleAdapter extends ApplicationAdapter {
	private Camera mCamera;
	private Environment mEnvironment;
	private Shader mShader;
	private Model mModel;
	private Renderable mRenderable;
	private CameraInputController mController;
	private RenderContext mRenderContex;

	@Override
	public void create () {
		mCamera = new PerspectiveCamera(100f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mCamera.position.set(120f, 0f, 300f);
		mCamera.lookAt(0f, 0f, 0f);
		mCamera.near = 0.1f;
		mCamera.far = 10000f;

		mEnvironment = new Environment();
		mEnvironment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		mEnvironment.add(new DirectionalLight().set(Color.WHITE.mul(0.6f), new Vector3(0, -1, 0)));
		mEnvironment.add(new DirectionalLight().set(Color.valueOf("#0C056D").mul(0.6f), new Vector3(0, 1, 0)));
		mEnvironment.add(new PointLight().set(Color.valueOf("#590D82"),new Vector3(200, 300, 400), 0.5f));
		mEnvironment.add(new PointLight().set(Color.valueOf("#590D82"),new Vector3(-200, 300, 400), 0.5f));

		ModelBuilder builder = new ModelBuilder();
		mModel = builder.createSphere(240f, 240f, 240f, 20, 20, new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				Usage.Position|Usage.Normal);
		NodePart part = mModel.getNode("node1").parts.get(0);

		mRenderable = new Renderable();
		mRenderable.meshPart.set(part.meshPart);
		mRenderable.material = part.material;
		mRenderable.environment = mEnvironment;
		mRenderable.worldTransform.idt();

		mRenderContex = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));

		mShader = new DefaultShader(mRenderable);
		mShader.init();

		mController = new CameraInputController(mCamera);
		Gdx.input.setInputProcessor(mController);
	}

	@Override
	public void render () {
		mController.update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		mRenderContex.begin();
		mShader.begin(mCamera, mRenderContex);
		mShader.render(mRenderable);
		mShader.end();
		mRenderContex.end();
	}
	
	@Override
	public void dispose () {
		mShader.dispose();
		mModel.dispose();
	}
}