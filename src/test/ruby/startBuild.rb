#!/usr/bin/env ruby
# encoding: utf-8
require "slop"
#TODO (che,2.6) : Other Jenkins Job Parameter
opts = Slop.parse do |o|
  o.string '-u', '--username', '--user', 'Apg Jenkins user'
  o.string '-h', '--hostname', '--host', 'Jenkins host'
  o.string '-t', '--apitoken', '--token', 'Jenkins user token'
  o.string '-j', '--jobname', '--job', 'Jenkins Job name', default: "TestDeclarativeWithTargetSystemMappings"
  o.separator ''
  o.separator 'other options:'
  o.string '-f', '--filename', '--upload', "Patch File to upload as Job Parameter to Jenkins"
  o.string '-p', '--parameter', '--jobparameter', "Jenkins Job Parameter"
  o.bool '-l', '--list', 'List available Jenkinsjobsr'
  o.on '--help' do
    puts o
    exit
  end
end
jobs = {"TestDeclarativeErrorHandling" => "Some parameter", "TestDeclarativeWithTargetSystemMappings" => %Q@{"parameter": [{"name": "testParameter", "value": "Something else Whatever"}, {"name":"patchFile.json", "file":"file0"}]}@}
if (opts[:list])
  puts "Available Jobs:"
  puts jobs.keys
  puts
end
if (!(opts[:user] and opts[:host] and opts[:token] and opts[:job]))
  puts "Missing mandatory options: user, host, token and/or job"
  puts opts
  exit
end
if !jobs["#{opts[:job]}"]
  puts "Job #{opts[:job]} does not exist. Available Jobs:"
  puts jobs.keys
  puts
  exit
end
jobParameter = jobs["#{opts[:job]}"]
if (/file0/ =~ jobParameter and !opts[:filename])
  puts "#{opts[:job]} needs fileparameter"
  puts "Parmeter ob job: #{jobParameter}"
  puts opts
  exit
end
puts jobParameter
crumbCurlCmd = %Q@curl -s -u "#{opts[:user]}:#{opts[:token]}" @ + "'" + opts[:host] + %Q@/crumbIssuer/api/xml?xpath=concat(//crumbRequestField,":",//crumb)@ + "'"
crumb = `#{crumbCurlCmd}`
puts "<#{crumb}>"
curlCmd = %Q@curl -v #{opts[:host]}/job/#{opts[:job]}/build -s -X POST -u "#{opts[:user]}:#{opts[:token]}"  -H "#{crumb}" @
if opts[:filename]
  curlCmd += "-F file0=@#{opts[:filename]}"
end
curlCmd += +%Q@ -F json='#{jobParameter}'@
puts "<#{curlCmd}>"
system "#{curlCmd}"
  