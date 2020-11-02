## Integrating CommCare with third party Identity Systems


CommCare supports [Android Callouts](https://github.com/dimagi/commcare-android/wiki/Intent-Callout-to-External-Application) based integration with third party Identity Providers that facilitate services like Biometric Enrollment and Identification. Using Android Intent Callouts, it's possible for someone to configure a CommCare app that can call a third party Android based Identity Provider and receive the enrolled case data back into CommCare app. 

If you are an Identity Provider and wants to integrate with CommCare, you can follow this guide to know about Android Intent contracts CommCare supports out of the box. 
To get access to generic CommCare data models, you need to first add the CommCare support library as a dependency [as described over here](https://github.com/dimagi/commcare-support-library#installation)


We define some of the common Identity Provider workflows below and how you can make changes in the Identity Application to support these workflows with CommCare. 


#### Enrolling a Beneficiary

You can have CommCare call your app in a form to register biometrics for a beneficiary. This beneficiary enrollment should create a new guid for the beneficiary in the Identity Provider.
Often you will need this generated guid to be passed back to CommCare so that it can be saved as a case property. In order to do so, you can utilize the `Registration` construct as follows. 
You can have CommCare call your app in a form to register biometrics for a beneficiary. This beneficiary enrollment should create a new guid for the beneficiary in the Identity Provider. This guid needs to be passed back to CommCare to be saved as a case property as follows 

````
Intent intent = new Intent();
Registration registration = new Registration(guid);
intent.putExtra(IDENTITY_REGISTRATION, registration);
setResult(RESULT_OK, intent);
````


#### Search or Identify a Beneficiary


An Identification workflow should result into a list of matches corresponding to a beneficiary biometric. These list of matches can be passed back to CommCare as follows - 

````
Intent intent = new Intent();
ArrayList<Identification> identifications = new ArrayList<>();

// add matches to identifications like 
identifications.add(new Identification(matchId, confidence, tier));

intent.putParcelableArrayListExtra(IDENTITY_IDENTIFICATIONS, identifications);
setResult(RESULT_OK, intent);
````

An `Identification` object comprises of the guid of the match, the matching score `confidence` and a matching `tier` based on the `confidence` that identifies if it's a good match or not.

#### Verify a Beneficiary

Verification is a 1:1 search that is used when you want to confirm whether a beneficiary is who they say are. Given a beneficiary's `guid` as input from CommCare, a verification response should be constructed as follows - 

````
Intent intent = new Intent();
Verification verification = new Verification(confidence, tier, guid);
intent.putExtra(IDENTITY_VERIFICATION, verification);
setResult(RESULT_OK, intent);
````
