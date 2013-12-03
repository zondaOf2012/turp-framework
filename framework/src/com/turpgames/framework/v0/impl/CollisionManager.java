package com.turpgames.framework.v0.impl;

import java.util.List;

import com.turpgames.framework.v0.ICollidable;
import com.turpgames.framework.v0.ICollisionGroup;
import com.turpgames.framework.v0.util.CollisionDetector;

public class CollisionManager extends Manager<ICollisionGroup> {
	public CollisionManager() {
	}

	@Override
	protected void execute(ICollisionGroup group) {
		if (!group.isActive())
			return;

		List<ICollidable> first = group.getFirst();
		List<ICollidable> second = group.getSecond();
		for (int j = 0; j < first.size(); j++) {
			for (int k = 0; k < second.size(); k++) {
				CollisionDetector.detect(group, first.get(j), second.get(k));
			}
		}
	}
}
