package com.karkoon.dungeoncrawler;

import android.os.Bundle;
import ashlified.DungeonCrawler;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        config.resolutionStrategy = new RatioResolutionStrategy(16f / 9f);
        initialize(new DungeonCrawler(), config);
    }
}
