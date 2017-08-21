# Mara Android User Help Library

This library lets you easily add a support setion to your app.

## Usage

Add this to the `build.gradle` file of your module

    dependencies {
        compile 'com.sendyit.mara:mara:0.5.0'
    }

## Sample Code

First you need to create an instance of Mara:

    Mara mara = new Mara(getApplicationContext(), Constants.GET_COLLECTION, Constants.POST_ARTICLE_REVIEW, Constants.POST_FORM);

All you need to do now is call `showHelp();` to show the support section.

## Parameters

You're going to need some parameters beforehand in order to successfully show your support content:

+ **Context**: This is the context of your current activity/fragment.
+ **Collection Endpoint**: This is the end point from which Mara can get your entire support JSON object.
+ **Post Article Review Endpoint**: This is the end point to which Mara can post a boolean whether the user found the article useful or not.
+ **Post Form Endpoint**: This is the end point to which Mara can post form data if the article required form feedback.

You must provide all of these parameters or else you will receive an error.

## JSON Support Data

Here's the schema for the data required from the collection end point. The collection is just a JSON object with your categories, sub-categories and articles.

[JSON Schema for support data](https://github.com/sendyers/mara_help/blob/master/schema.txt).

## Data Examples

Here is a sample JSON Object that Mara can consume and display. It includes actions with deeplinks and forms for feedback:

[Sample JSON support data](https://github.com/sendyers/mara_help/blob/master/sample_data.txt).

## Deep Links

You'll need to pre-define deep links in your app, and add the links in your actions in order to perform actions in your app directly from Mara.
