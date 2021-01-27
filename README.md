# GithubRepoApp

Sample app using GitHub api's(https://api.github.com/). App has the below features, 
1. Connect to the GitHub API (using my github token)
2. Get list of all Github users who have more than 1 repository(Ref : https://docs.github.com/en/rest/reference/search)
3. Get all the repository for a User(Ref : https://docs.github.com/en/rest/reference/repos#get-a-repository)
4. Get all the commits for a repository (Ref : https://docs.github.com/en/rest/reference/repos#list-commits)

# Libraries used :
Pattern/Architecture : Clean Architecture along with Androids Jetpack libaries.

In addition to the above, using
* Coroutines - for async task 
* Koin - for dependency injection
* Glide - display images 
* OkHttp MockWebServer - assists in testing API serices
