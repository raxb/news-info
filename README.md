# news-info

In today’s world, information is abundant and everywhere but not all that glitters are gold, we need to read through multiple sources, we need to process, we need to synthesis, we need to trial and error, we need to get opinions and in the last we come to profound realization that this information is not right, this information is misleading, this information is  might not be the relevant, and we have the time lost, resources lost.
Having first-hand information at hand helps in making informed decisions precisely.
How can this be eliminated, how to confirm the authenticity, relevancy?
1.	It has to be from the right sources with enclosed proofs (should be incentives to post content)
2.	Endorsed by experts, interest groups etc (should be incentives to do so)
3.	Evaluable with preliminary information (consumer should do this)
Build a system to provide information, curb fake, misleading, irrelevant information.
Start with local news!

__1	Confirmed by independent Sources	Logical, consistent with other relevant information, confirmed by independent sources.
2	Probably true	Logical, consistent with other relevant information, not confirmed.
3	Possibly true	Reasonably logical, agrees with some relevant information, not confirmed.
4	Doubtful	Not logical but possible, no other information on the subject, not confirmed.
5	Improbable	Not logical, contradicted by other relevant information.
6	Difficult to say	The validity of the information cannot be determined.__


News Reporter:
1)	Submit a topic with date, location of event, identity, and timestamped images if any
2)	15 minutes to modify/update the topic
3)	Tag the topic (geographically)

News Reader:
1)	Endorses the topic and its authenticity
a.	Should be independent of the topic and geography (50:50 % geography confirmation)
b.	Should be able to state any 1 of the above 6
2)	Initial endorsers receive renown and token points
3)	Post 100 endorsers the REPORTER will be receiving 2x renown and token points for the topic submitted

System:
1)	Records this information in a private blockchain per category
a.	After 100 endorsers
b.	After 24 hours of reporting.

Use Cases Flow
1.	Reporter submits a news with location details and this news will be mapped to the reporter profile (one-to-many mapping). And this news will not be published to the news feed until 15 minutes is elapsed (window for onetime-modification).
2.	NewsReporter table will have reporterId and news_transactionId (one-to-many). Only the reporter will be able to modify the news that he/she reports.
3.	Once it goes to the news feed (window for onetime-modification lapses), the Endorsers will be able to see the news-topic, location occurrence, and in a scale of (1-6 as in table above) the news will be polled. Read-only polling hence once polled it can’t be reverted.
4.	Endorser’s profile will be updated for the votes they are casting for the news (one-to-many). In Endorser’s profile, news_transactionId and their vote against the news will be recorded.
5.	NewsEndorser table will capture the news_transactionId and EndorsersId sorted with date_time logic (for one news_transactionId there can be n EndorsersId)
6.	For a given news_transactionId, from the NewsEndorsers table the count will be take and once it exceeds 100 votes then this information will move to blockchain DLT.
7.	Then the information from the blockchain will be published as the News.
8.	From the NewsEndorser table, for the news_transactionId the EndorsersId sorted with date_time will be fetched and tokens will be paid from 100 to 1 for all the Endorsers.
9.	For the news_transactionId, in NewsReporter table the reporterId will be obtained and 500 tokens will be paid with increased stake.

Negative cases
1.	Reporter submits the same news.
2.	Endorsers are a self-interest group.
3.	Reporter-Endorser partnerships.

Database Structure
->NewsDetailsEntry	(1-1)->NewsEndorserFeed	(N-1)->NewsEndorser
			(N-1)->NewsReporter		(1-1)->NewsBlockchain


