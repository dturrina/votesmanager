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

import android.content.Intent;
import android.os.Bundle;

/**
 * Opening Activity.
 * 
 * This is the first Activity the Application will open.
 * At the moment, all the Application does is opening another Activity once opened; in the future,
 * it is planned the Application to bring help wizards the user to configure the Application itself,
 * and this Activity will be their caller.
 * 
 * @author dturrina
 * @see    SuperActivity
 * @since  0.1
 */
public class StartActivity extends SuperActivity {

	private static final String LOG_PREFIX = StartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_start); // No need to set a content, now.
        
        /** Automatically start another activity via an Intent.
         * Should be changed in the future in order to select
         * whether to start config or main activity.
         */
        Intent autoload = new Intent(this, CompetitorListActivity.class);
        this.startActivity(autoload);
        this.finish();
    }
    
}
