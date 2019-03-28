package base;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BulletManager {

	private ArrayList<Bullet> mMyBullets;
	private ArrayList<Bullet> mOtherBullets;
	private Date mLastBulletRecievedDate;

	public BulletManager() {
		mMyBullets = new ArrayList<>();
		mOtherBullets = new ArrayList<>();
		mLastBulletRecievedDate = new Date();
	}

	public void update() {
	

		for (int index = mMyBullets.size() - 1; index >= 0; index--) {
			if (!mMyBullets.get(index).Update()) {
				mMyBullets.remove(index);
				// removeMyBullets.add(mMyBullets.get(index));
			}

		}
		for (int index = mOtherBullets.size() - 1; index >= 0; index--) {
			if (!mOtherBullets.get(index).Update()) {
				mOtherBullets.remove(index);
				// removeMyBullets.add(mMyBullets.get(index));
			}

		}
		// for (Bullet bullet : mOtherBullets) {
		// if (!bullet.Update()) {
		// removeOtherBullets.add(bullet);
		// }
		// }
		//
		// mOtherBullets.removeAll(removeOtherBullets);
	}

	public void draw(SpriteBatch sb) {
		for (Bullet bullet : mMyBullets) {
			bullet.Draw(sb);
		}

		for (Bullet bullet : mOtherBullets) {
			bullet.Draw(sb);
		}
	}

	public JSONArray getMyBulletsToString() {
		JSONArray allBullets = new JSONArray();
		for (Bullet bullet : mMyBullets) {
			allBullets.put(bullet.toJSON());
		}
		return allBullets;
	}

	public JSONArray getOtherBulletsToString() {
		JSONArray allBullets = new JSONArray();
		for (Bullet bullet : mOtherBullets) {
			allBullets.put(bullet.toJSON());
		}
		return allBullets;
	}

	public void addToMyBullet(Bullet newBullet) {
		mMyBullets.add(newBullet);
	}

	/**
	 * Don't forget to start drawing the bullets asap.
	 **/
	public void addToOthersBullets(JSONArray othersBullets) {
		for (int index = 0; index < othersBullets.length(); index++) {
			// Create bullet from JSON
			Bullet incomingBullet = new Bullet();
			incomingBullet.toBullet((JSONObject) othersBullets.get(index));

			// Check if bullet not in the list already, then add it.
			if (!mOtherBullets.contains(incomingBullet)) {
				mOtherBullets.add(incomingBullet);
			}

			// Check if the date of this new bullet is newer than my current date. if yes,
			// then change to the new date.
			if (incomingBullet.getCreationDate().getTime() > mLastBulletRecievedDate.getTime())
				mLastBulletRecievedDate = incomingBullet.getCreationDate();
		}
	}
}
