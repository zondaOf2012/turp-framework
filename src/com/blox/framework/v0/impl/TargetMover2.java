package com.blox.framework.v0.impl;

import com.blox.framework.v0.IMovable;
import com.blox.framework.v0.util.Game;
import com.blox.framework.v0.util.Vector;

public class TargetMover2 extends TargetMover {
	final static int SpeedUp = 0;
	final static int SlowDown = 1;

	private float ax;
	private float ay;

	private float distAll;
	private float slowDownStartPos;
	private float slowDownStartTime;
	private int state;
	private float slowDownTimeElapsed;

	private SlowDownCalculator calcX;
	private SlowDownCalculator calcY;

	public TargetMover2(float duration, float slowDownStartPos, float slowDownStartTime) {
		super(duration);

		this.slowDownStartPos = slowDownStartPos;
		this.slowDownStartTime = slowDownStartTime;

		this.calcX = new SlowDownCalculator();
		this.calcY = new SlowDownCalculator();
	}
	
	@Override
	public void updateRoute(Vector start, Vector end) {
		super.updateRoute(start, end);
		this.distAll = end.dist(start);
	}

	@Override
	protected void updateVelocity() {
		vx = 0;
		vy = 0;

		float slowDownTime2 = duration * slowDownStartTime * duration * slowDownStartTime;
		ax = 2 * (end.x - start.x) * slowDownStartPos / slowDownTime2;
		ay = 2 * (end.y - start.y) * slowDownStartPos / slowDownTime2;
	}

	@Override
	public void move(IMovable movable) {
		if (stopped)
			return;

		float dt = Game.getDeltaTime();
		Vector loc = movable.getLocation();

		if (target == null) {
			target = end;
			loc.x = start.x;
			loc.y = start.y;
		}
		else {
			float dist = target.dist(loc);

			if (dist > distToTarget) {
				loc.x = target.x;
				loc.y = target.y;

				if (notifyMoveEnd(movable))
					return;
				
				if (!looping) {
					stopped = true;
					return;
				}

				state = SpeedUp;
				slowDownTimeElapsed = 0;

				target = target == end ? start : end;

				float slowDownTime2 = duration * slowDownStartTime * duration * slowDownStartTime;
				ax = 2 * (target.x - loc.x) * slowDownStartPos / slowDownTime2;
				ay = 2 * (target.y - loc.y) * slowDownStartPos / slowDownTime2;

				vx = 0;
				vy = 0;

				distToTarget = distAll;
			}
			else {
				distToTarget = dist;

				if (state == SpeedUp && distToTarget / distAll < 1 - slowDownStartPos) {
					state = SlowDown;
					calcX.update(vx, duration * (1 - slowDownStartTime), target.x - loc.x);
					calcY.update(vy, duration * (1 - slowDownStartTime), target.y - loc.y);
				}
			}
		}

		if (state == SlowDown) {
			slowDownTimeElapsed += dt;
			ax = calcX.calculateAcceleration(slowDownTimeElapsed);
			ay = calcY.calculateAcceleration(slowDownTimeElapsed);
			vx = calcX.calculateVelocity(slowDownTimeElapsed);
			vy = calcY.calculateVelocity(slowDownTimeElapsed);

			loc.x += vx * dt;
			loc.y += vy * dt;
		}
		else {
			loc.x += 0.5f * ax * dt * dt + vx * dt;
			loc.y += 0.5f * ay * dt * dt + vy * dt;

			vx += ax * dt;
			vy += ay * dt;
		}
	}

	private static class SlowDownCalculator {
		private float a;
		private float b;
		private float c;

		public void update(float v, float t, float d) {
			c = v;
			b = (6 * d - 4 * v * t) / (t * t);
			a = -((b * t) + c) / (t * t);
		}

		public float calculateVelocity(float t) {
			return a * t * t + b * t + c;
		}

		public float calculateAcceleration(float t) {
			return 2 * a * t + b;
		}
	}
}