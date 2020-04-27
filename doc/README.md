# How To Use
* Suppose you have the docker stack up and running on your localhost
* Note: You need to put `debstation.nerdpol.ovh` into your `/etc/hosts` file and make it point to `127.0.1.1`, otherwise the proxy and the SSL certificate from letsencrypt won't work. My entry in `/etc/hosts` looks like this:
```
...

127.0.1.1   debstation.nerdpol.ovh

...
```
* Open [https://debstation.nerdpol.ovh](https://debstation.nerdpol.ovh) to see list of all posts
* Open [https://debstation.nerdpol.ovh/add](https://debstation.nerdpol.ovh/add) to add a new post
* Username: billig
* Password: bier
* See [https://debstation.nerdpol.ovh/api/postings](https://debstation.nerdpol.ovh/api/postings) for REST API call to get all the posts

# How To Export Posts
* Note: I will have to come up with a cool idea to nicely export all this

