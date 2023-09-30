<h1 align="center">
    <br>
        <img src="logo/VKSponsorBlockLogo.png" alt="Logo"/>
    <br>
    VK SponsorBlock Server
</h1>


VK SponsorBlock is a browser extension for VK Video inspired by the [SponsorBlock](https://github.com/ajayyy/SponsorBlock/tree/master#credit)
extension for YouTube. VK SponsorBlock allows you to save and share video segments with ads with the community, and skip them automatically.

This repository represents the server for this extension.

# API

The project has a built-in swagger for API documentation.

# Requirements

To build and run the project, you will need the following:

- Docker
- Docker-compose

# Build and run

To build and run the server yourself, you need to do the following things:

- Clone repository
- Change the database user and password in files [application.yaml](src%2Fmain%2Fresources%2Fapplication.yaml) and [docker-compose.yaml](docker-compose.yaml)
- Change the URL in the same files
- Change the login and password for pgAdmin in the file [docker-compose.yaml](docker-compose.yaml) or after the launch and the first login[docker-compose.yaml](docker-compose.yaml)

# License

This project is under the GPL-3.0 license.
