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

**Put here the image**

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

**Put a image**

* You should change the URL by the environment variable **VYNILS_BASE_API_URL_DEBUG** url **http://10.0.2.2:3000/** this is a standard ip where the emulator listen the localhost

```properties
VYNILS_BASE_API_URL_DEBUG="http://10.0.2.2:3000/"
```

**Put a image**

4. Open the Android studio and sync gradle 🐘

* Go to the file

* And select "Sync project with gradle"

**Put a image**

5. Rebuild the project (recommend)

* Go to "Build"

* Click on Rebuild Project 👷🏻‍♀️

6. Now you can run the project

* You should select your emulator or own device

**Put here a image**

* Finally, you should run the project

**Put here a image**







