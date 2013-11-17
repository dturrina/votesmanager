/*
 * Copyright (c) 2013, Forseti and the VotesManager project. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package it.forseti.votesmanager.activities;

import it.forseti.votesmanager.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Superclass for application Activities.
 * 
 * The "mother of all Activities"; it brings the Options menu initialization just once, in order
 * for it to be used through all Activities.
 * 
 * @author dturrina
 * @see    android.app.Activity
 * @since  0.1
 */
public class SuperActivity extends Activity {

	private static final String LOG_PREFIX = SuperActivity.class.getSimpleName();

    /**
     * This method is called at the creation of the Activity.
     * @see android.app.Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * This method brings a single Options menu for all Activities.
     * @see android.app.Activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Inflate the menu; this adds items to the action bar
         * if it is present.
         */
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return true;
    }

}

