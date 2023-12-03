# Description
This is an application 📱 to management, review and check information about albums 💿, artists 👩‍🎤and music 🎶🎸🥁🎹🎼

<p style="text-align:center">
    <img src="https://github.com/The-Software-Design-Company/tsdc-vynils-app/actions/workflows/pull-request-from-branch-to-master.yml/badge.svg" />
    <img src="https://github.com/The-Software-Design-Company/tsdc-vynils-app/actions/workflows/branches-action.yml/badge.svg" />
</p>

# Made with
[![Kotlin](https://img.shields.io/badge/kotlin-7f52ff?style=for-the-badge&logo=kotlin&logoColor=white&labelColor=000000)]()
[![Android](https://img.shields.io/badge/Android-3ddc84?style=for-the-badge&logo=android&logoColor=white&labelColor=000000)]()

# Prerequires
- Compile SDK 34
- minSdk 21
- targetSdk 33
- JVM Target 1.8 ☕️

# Run in your local machine 🖥️

## Staging API

# ⚠️ Important:
* The api is deployed in heroku and the service when it isn't being used it falls asleep 😴, you need to wake the service up

* Before doing any test 🧪 you need to call the healthCheck to wake the service up, this is the endpoint example

```sh
$ curl https://tsdc-vynils-staging-api-d6a4f176b374.herokuapp.com/health
```

* After that you should receive the next response:

```json
{
    "name":"vinyls-backend-miso",
    "version":"1.1.3",
    "environment":"staging",
    "status":200,
    "uptime":177.56916666666666
}
```



## Run the api in local

`Note: This option is if you want to run with the API in your local machine`

1. Firstly you need to download the repository with the api **tsdc-vynils-api**

```sh
$ git clone https://github.com/The-Software-Design-Company/tsdc-vynils-api.git
```

2. You need to check the node nest prerequires

* node **v12.22.12** 
* npm **6.14.16**
* Docker & docker-compose 🐳
* For Linux 🐧 and mac 🍎 you can use makefile.
* For Windows 🪟 you can use bash function.

3. Install dependencies
```sh
# Go to the folder
$ cd tsdc-vynils-api

# If you have nvm you can set the node version
$ nvm use v12.22.12

```

`Note: If you don't have nvm you can download the specific node version in the next url`

[Node 12.22.12](https://nodejs.org/download/release/v12.22.12/)

```sh
# Install dependencies
$ npm install 
```

4. Configuration environment you should have the **.env.dev** please follow the file **.env.example** you should have something like this

```sh
NODE_ENV=develop
DB_HOST=db
DB_USERNAME=postgres
DB_PASSWORD=postgres
DB_DATABASE=vynils
DB_SSL_OPTION=false
PORT=3000
DEBUG=true
```

5. Run in docker 🐳

```bash
# With Linux 🐧 or Mac 🍎
make docker-dev-up

# Command to run with nginx  and proxy reverse 
make docker-up

# With Windows 🪟
source run.sh; docker_dev_up

# Command to run with nginx  and proxy reverse 
source run.sh; docker_up

# With docker compose for all Operative Systems

docker compose -f=docker-compose.develop.yml up --build

# Command to run with nginx  and proxy reverse

docker compose up --build
```

6. Make sure that all microservices are running

* Executing this command

```bash
    docker ps
```

![Screenshot 2023-11-05 at 20 56 11](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/10298615/3b1731b0-f1ff-4b15-8c5b-663f79239f99)


7. You can try the application with the next cURL command

```sh
$ curl --location 'http://localhost:3000/albums'
```
## Run the android application 🤖 📱

1. Firstly, you need clone the application
```sh
$ git clone https://github.com/The-Software-Design-Company/tsdc-vynils-app.git
```

2. You need to go to the project folder

```sh
$ cd tsdc-vynils-app
```

3. If you want to use the local machine 🖥️ with docker 🐳

* You should go to the **gradle.properties** on the root of the project

![Screenshot 2023-11-05 at 21 06 52](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/10298615/949c1464-cbd8-4de3-ac12-3d178ad28054)


* You should change the URL by the environment variable **VYNILS_BASE_API_URL_DEBUG** url **http://10.0.2.2:3000/** this is a standard ip where the emulator listen the localhost

```properties
VYNILS_BASE_API_URL_DEBUG="http://10.0.2.2:3000/"
```
![Screenshot 2023-11-05 at 22 11 10](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/10298615/3f63ea9c-e218-45d1-9c9b-b13eaaec2748)

4. Open the Android studio and sync gradle 🐘

* Go to the file

* And select "Sync project with gradle"

![Screenshot 2023-11-05 at 21 02 41](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/10298615/c2483726-2b9c-4994-a109-33e5a97865a9)


5. Rebuild the project (recommend)

* Go to "Build"

* Click on Rebuild Project 👷🏻‍♀️

![Screenshot 2023-11-05 at 21 03 14](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/10298615/7d8692f8-dfe5-417e-9995-ab9f99b9d693)


6. Now you can run the project

* You should select your emulator or own device

![Screenshot 2023-11-05 at 21 11 56](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/10298615/dfe8d904-3e91-4c00-ae30-aca0ee152d07)

* Click un run ▶️

![Screenshot 2023-11-05 at 21 15 08](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/10298615/5df61247-0bd2-470b-9119-e27a268fe2ee)


* Finally, you can see the application 📱

![WhatsApp Image 2023-12-02 at 10 33 03 PM](https://github.com/The-Software-Design-Company/tsdc-vynils-app/assets/48004607/b762a93d-e908-4b1f-89e3-34fb1a739e67)






