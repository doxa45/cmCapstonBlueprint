#
# Cookbook Name:: solr
# Attributes:: default
#
# Copyright 2017, CoreMedia AG
#

# <> define solr version to use
default['blueprint']['solr']['version']  = '6.6.0'
# <> define solr download url
default['blueprint']['solr']['url']      = "http://archive.apache.org/dist/lucene/solr/#{node['blueprint']['solr']['version']}/solr-#{node['blueprint']['solr']['version']}.tgz"
# <> define artifact checksum
default['blueprint']['solr']['checksum'] = '6b1d1ed0b74aef320633b40a38a790477e00d75b56b9cdc578533235315ffa1e'
# <> define solr home
default['blueprint']['solr']['solr_home'] = '/opt/coremedia/solr-home'
# <> define solr index data directory
default['blueprint']['solr']['solr_data_dir'] = '/var/coremedia/solr-data'
# <> define solr dir
default['blueprint']['solr']['dir']      = '/opt/solr'
# <> define solr port
default['blueprint']['solr']['port']     = '40080'
# <> define solr JMX RMI port
default['blueprint']['solr']['jmx_port'] = '40099'
# <> define whether Solr should activate the JMX RMI connector to allow remote JMX client applications to connect
default['blueprint']['solr']['jmx_enable'] = 'false'
# <> define solr pid dir
default['blueprint']['solr']['pid_dir'] = '/var/run/solr/'
# <> define solr log dir
default['blueprint']['solr']['log_dir'] = '/var/log/solr/'
# <> define solr user
default['blueprint']['solr']['user']     = 'solr'
# <> define solr group
default['blueprint']['solr']['group']    = 'solr'
# <> set to true to install java
default['blueprint']['solr']['install_java'] = false
# <> set to the java home used for solr
default['blueprint']['solr']['java_home'] = '/usr/lib/jvm/java'
# <> define solr_java options
default['blueprint']['solr']['java_mem'] = '-Xms128M -Xmx512M'
# <> define the maven group id for solr-config.zip
default['blueprint']['solr']['config_zip_group_id'] = 'com.coremedia.blueprint'
# <> define the maven artifact id for solr-config.zip
default['blueprint']['solr']['config_zip_artifact_id'] = 'solr-config'
# <> define the maven version for solr-config.zip
default['blueprint']['solr']['config_zip_version'] = node['blueprint']['default_version'] ? node['blueprint']['default_version'] : '1-SNAPSHOT'
# <> The repository url from which the webapp artifacts are downloaded
default['blueprint']['maven_repository_url'] = 'file://localhost/maven-repo/'
