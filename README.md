[![Sample Banner](views/Sample.png)][ss1]

# SampleApp-Payments-Java
SampleApp-Payments-Java

<p>Welcome to the Intuit Developer's Java Sample App for Payments API operations.</p>
<p>This sample app is meant to provide working examples of how to integrate your app with the Intuit Small Business ecosystem. Specifically, this sample application demonstrates how to use Payments API using Java SDK as well as samples without the use of SDK</p>

<ul>
	<li>All operations using Java SDK</li>
	<li>Create Charge (Without SDK) </li>
	<li>Create Charge Using Token (Without SDK) </li>
	<li>Retrieve Charge. (Without SDK) </li>
	<li>Create Token.(Without SDK)  </li>
	<li>All operations are performed using OAuth2.</li>
</ul>

<p>Please note that while these examples work, features not called out above are not intended to be taken and used in production business applications. In other words, this is not a seed project to be taken cart blanche and deployed to your production environment.</p>  

<p>For example, certain concerns are not addressed at all in our samples (e.g. security, privacy, scalability). In our sample apps, we strive to strike a balance between clarity, maintainability, and performance where we can. However, clarity is ultimately the most important quality in a sample app.</p>

<p>Therefore there are certain instances where we might forgo a more complicated implementation (e.g. caching a frequently used value, robust error handling, more generic domain model structure) in favor of code that is easier to read. In that light, we welcome any feedback that makes our samples apps easier to learn from.</p>

## Table of Contents

* [Requirements](#requirements)
* [First Use Instructions](#first-use-instructions)
* [Running the code](#running-the-code)


## Requirements

In order to successfully run this sample app you need a few things:

1. Java 1.8
2. A [developer.intuit.com](http://developer.intuit.com) account using OAuth2.
3. An app on [developer.intuit.com](http://developer.intuit.com) using Payments API and the associated client id, and client secret.
4. One sandbox company, connect the company with your app and generate the oauth2 tokens. Make sure to set payment scope during the oauth process.
 

## First Use Instructions

1. Clone the GitHub repo to your computer
2. Import the project in Eclipse or any other IDE of your choice
3. In [`config.properties`](src/main/resources/config.properties) fill in the [`config.properties`](src/main/resources/config.properties) file values (clientId, clientSecret, refreshtToken, accessToken).

## Running the code

This app is directed to provide individual sample code for CRUD operations for various entities.
Each class has a main method that can be run individually.

Steps described below is to run the class for creating a customer in Eclipse IDE.

1. Go to ChargeCreate.java in package com.intuit.sample.payment
2. Right click the file and Run as Java application
3. On the console you'll see the log being generated with the charge id.

Follow similar steps for other classes.

[ss1]: https://help.developer.intuit.com/s/samplefeedback?cid=9010&repoName=SampleApp-Payments-Java
