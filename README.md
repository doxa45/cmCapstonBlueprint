# CoreMedia Blueprint

## Documentation

Refer to the Getting Started chapter of the product's Developer Manual for a detailed description of the project workspace.

## Structure

The workspace is separated into three major directory hierarchies:

* The `modules` folder contains all library- and application-modules.
* The `test-data` folder contains test content and test user definitions. Currently the test content is packaged by 
the boxes module.  Extensions bring their own content, also in directories named 'test-data'.  The name test-data and
the two subdirectories content and users are fix and essential for our build and deployment processes.

## Deployment

The `deployment` folder contains an out-of-the-box deployment example using the configuration management framework _Chef_.

Refer to [deployment/chef/README.md](./deployment/chef/README.md) for details.

## Updating the Workspace

CoreMedia provides this dedicated [CoreMedia Blueprint GitHub mirror repository](https://github.com/coremedia-contributions/coremedia-blueprints-workspace) for customers and partners.

Simply use GitHubs web frontend to visually compare changes between release versions. Each release is aggregated in a [single git commit](https://github.com/coremedia-contributions/coremedia-blueprints-workspace/commits/master).

CoreMedia heavily encourages you to use one of the following approaches:

### Updating via Git

Instead of extracting the ZIP archive from the CoreMedia download site, you can simply use Git to fetch updates and merge them with your own customizations.

### Updating via Patch files

Although CoreMedia recommends to use Git, you can keep using your favorite source code management system by applying release changes patch by patch.

For example, CoreMedia release changes are mirrored in https://github.com/coremedia-contributions/coremedia-blueprints-workspace

Simply add ``.patch`` to the commit URL to be able to download in patch format (hidden GitHub feature).

In some cases GitHub won't generate the patch (e.g. `error: too big or took too long to generate` or `Content containing PDF or PS header bytes cannot be rendered from this domain for security reasons.`).

Use ``git format-patch -1 <commit>`` on the command-line as a workaround (<http://git-scm.com/docs/git-format-patch>).

Please contact [support@coremedia.com](mailto:support@coremedia.com) if you need further assistance!

### Docker Setup

If you plan to use this workspace in conjunction with the CoreMedia Docker setup, you have to make sure,
that you also check out the other two workspaces using the matching version tags. The tags on those repos are
named `cms-XXXX.Y`.

In order to use the docker setup make sure to build the blueprints product workspace first, then the applications repo and
finally the docker repo.

It is recommended that you check out the workspaces with the following file structure. The default content import, for example, expects the test data below the blueprint folder:

<root dir>
      |- blueprint (CoreMedia Blueprints Workspace)
      |- applications (CoreMedia Spring Boot Applications Workspace)
      `- deployment (CoreMedia Docker Deployment Workspace)

For more detailed information read the README.md files in the corresponding repositories.

* [CoreMedia Spring Boot Applications Workspace](https://github.com/coremedia-contributions/coremedia-spring-boot-applications)
* [CoreMedia Docker Deployment Workspace](https://github.com/coremedia-contributions/coremedia-deployment-docker)