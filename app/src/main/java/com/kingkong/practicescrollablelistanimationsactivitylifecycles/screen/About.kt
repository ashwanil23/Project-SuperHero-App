package com.kingkong.practicescrollablelistanimationsactivitylifecycles.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.kingkong.practicescrollablelistanimationsactivitylifecycles.R

@Composable
fun About(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(dimensionResource(R.dimen.medium)), // Add padding for better spacing
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "About SuperHeroApp",
            style = MaterialTheme.typography.h4, // Use a larger text style for the heading
            modifier = Modifier.padding(bottom = 16.dp) // Add space below the heading
        )
        Text(
            text = """
                Welcome to SuperHeroApp, your ultimate companion for exploring the world of superheroes! Whether you are a die-hard fan or a casual enthusiast, our app is designed to deliver an engaging and immersive experience like no other.

                Discover:
                Dive into a vast collection of superheroes with detailed profiles. Explore their backstories, superpowers, and the iconic moments that define them.

                Favorites:
                Keep track of your favorite heroes with ease. Create your personalized list and access it anytime, anywhere.

                Explore:
                Uncover new heroes and legendary tales from different universes. Our explore section is your gateway to discovering hidden gems and fan favorites.

                Profile:
                Manage your user profile to get a tailored experience. Customize your preferences, track your achievements, and more.

                Settings:
                Adjust app settings to suit your preferences. From notification controls to theme adjustments, tailor the app just the way you like it.

                About:
                Learn more about the vision and team behind SuperHeroApp. Discover how we aim to celebrate the world of superheroes and bring fans closer to their favorite characters.

                Feedback:
                Your opinion matters! Share your thoughts and suggestions with us. We're always looking to improve and create a better experience for our users.

                Notifications:
                Stay updated with the latest news, events, and updates from the world of superheroes. Never miss out on special announcements and new additions to the app.

                Achievements:
                Track your progress and earn achievements as you explore and engage with the app. Unlock special badges and rewards for your dedication.

                SuperHeroApp is more than just an app; it's a community of passionate fans and a celebration of superhero culture. Join us on this exciting journey and be part of a universe where heroes come to life.

                Join the Adventure. Be a Hero.
            """.trimIndent(),
            style = MaterialTheme.typography.body1, // Use a normal text style for the body
            modifier = Modifier.padding(bottom = 16.dp) // Add space below the text
        )
    }
}

