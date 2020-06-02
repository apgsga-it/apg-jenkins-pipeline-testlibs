#!/usr/bin/perl
use strict;
use warnings FATAL => 'all';
use Getopt::Long;
use Pod::Usage;


my $text = "";
my $help = 0;
my $error = 0;
GetOptions(
    'help'   => \$help,
    'text=s' => \$text,
    'error'  => \$error
) or pod2usage(2);
pod2usage(-exitval => 0) if $help or !($text);
die $text if $error;
print "$text", "\n";

__END__

=head1 NAME

 error.pl

=head1 SYNOPSIS

    error.pl [-help] [-text=SOMETEXT] -e

=head1 DESCRIPTION

   Die's with with some Text

=head1 ARGUMENTS

    -h|help          display this help and exits
    -m|man           displays the manual pages and exits
    -i|installDir=INSTALL_DIR Installation Directory , defaults to "/opt/jenkinstests"
    -t|task=TASKNAME runs the Gradle Task with name TASKNAME
    -l|list          list the avaible Gradle Pipeline Testtasks

=cut
