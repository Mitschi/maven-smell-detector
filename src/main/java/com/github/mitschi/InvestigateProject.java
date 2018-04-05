package com.github.mitschi;

import com.github.mitschi.smells.MavenSmell;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.LockFailedException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InvestigateProject {
    public static void main(String[] args) throws Exception{
        File projectFolder = new File(args[0]);
        File outputFile = new File(args[1]);
        PrintStream ps = new PrintStream(outputFile);

        File gitDir = new File(projectFolder, ".git");
        org.eclipse.jgit.lib.Repository repo = new FileRepository(gitDir);
        Git git = new Git(repo);

        Iterable<RevCommit> logs = git.log().all().call();
//        Iterable<RevCommit> logs = git.log().call(); //only master

        for (RevCommit rev : logs) {
//        System.out.println(rev.getName());
            try {
                resetRepo(rev.getName(), projectFolder);

                MavenSmellDetector mavenSmellDetector = new MavenSmellDetector();
                List<MavenSmell> smells = mavenSmellDetector.detectSmells(projectFolder);

                ps.print(rev.getName()+"  -->  "+formatDate(rev.getCommitterIdent().getWhen())+"; Smells: ");
                ps.println(smells.parallelStream().map(x -> x.getMavenSmellType()).collect(Collectors.toList()));
            }catch (Exception e) {
                System.out.print("error in "+rev.getName()+":   ");
                System.out.println(e.getMessage());
//                e.printStackTrace();
            }
        }
        ps.close();
    }

    private static String formatDate(Date when) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(when);
    }

    private static void resetRepo(String revision, File repoDir) throws IOException, GitAPIException {
        try {
            File gitFolder = new File(repoDir, ".git");
            Repository repository = FileRepositoryBuilder.create(gitFolder);
            Git git = new Git(repository);

            Set<String> removed = git.clean().setCleanDirectories(true).call();
//            for(String item : removed) {
//                System.out.println("Removed: " + item);
//            }
            try {
                Ref call = git.reset().setMode(ResetCommand.ResetType.HARD).call();
//                System.out.println("reset to: "+call.getName());
                git.checkout().setName(revision).call();
            } catch(JGitInternalException jgitex) {
                if(jgitex.getCause() instanceof LockFailedException) {
                    //try to remove the lock file
                    File lockFile = new File(gitFolder,"index.lock");
                    FileUtils.forceDelete(lockFile);
                    Ref call = git.reset().setMode(ResetCommand.ResetType.HARD).call();
//                    System.out.println("reset to: "+call.getName());
                    git.checkout().setName(revision).call();
                } else {
                    //unexpected
                    throw jgitex;
                }
            }

        } catch(Exception e) {
            System.out.println("Could not reset the repository, leaving it untouched and proceed: "+e.getMessage());
            System.out.println("Further info: "+e.getCause().getMessage());
        }
    }
}
