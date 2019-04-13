package com.examples.knightcube.xrcube;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
    private ArFragment arFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            renderModel(hitResult.createAnchor());
        });
    }

    private void renderModel(Anchor anchor) {
        ModelRenderable.builder()
                .setSource(this, Uri.parse("iron_man.sfb"))
                .build()
                .thenAccept(modelRenderable -> {
                    placeModel(modelRenderable,anchor);
                });
    }

    private void placeModel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);

        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);

//        anchorNode.setRenderable(modelRenderable);

        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }


}
