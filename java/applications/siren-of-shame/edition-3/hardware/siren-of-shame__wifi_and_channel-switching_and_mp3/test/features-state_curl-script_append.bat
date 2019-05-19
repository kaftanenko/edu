curl -D- ^
	-X POST -H "X-Atlassian-Token: nocheck" ^
	-F "file=@features_sample.jsn" ^
	-F "comment=MoMA Features Unit-Tests Status" ^
	-u akaftanenko:... ^
	-k ^
	-v ^
	https://wiki.impaq.de/rest/api/content/41779231/child/attachment
rem	-x http://localhost:8888 ^
