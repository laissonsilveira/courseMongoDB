package course.laissonsilveira;


public class Demos extends BaseTest {

    // private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    // private GithubUser laisson;
    // private Date date;
    //
    // public Demos() throws ParseException {
    // date = sdf.parse("04-04-2015");
    // laisson = new GithubUser("laisson");
    // laisson.fullName = "Laisson R. Silveira";
    // laisson.memberSince = date;
    // laisson.following = 1000;
    //
    // ds.save(laisson);
    // }
    //
    // @Test(dependsOnMethods = { "basicUser" })
    // public void repositories() {
    // Organization org = new Organization("mongodb");
    // ds.save(org);
    //
    // Repository morphia01 = new Repository(org, "morphia");
    // Repository morphia02 = new Repository(laisson, "morphia");
    //
    // ds.save(morphia01);
    // ds.save(morphia02);
    //
    // laisson.repositories.add(morphia01);
    // laisson.repositories.add(morphia02);
    //
    // ds.save(laisson);
    // }
    //
    // @Test(dependsOnMethods = { "repositories" })
    // public void query() {
    // Query<Repository> query = ds.createQuery(Repository.class);
    //
    // Repository repository = query.get();
    //
    // List<Repository> repositories = query.asList();
    //
    // Iterable<Repository> fetch = query.fetch();
    // ((MorphiaIterator) fetch).close();
    //
    // Iterator<Repository> iterator = fetch.iterator();
    // while (iterator.hasNext()) {
    // iterator.next();
    // }
    //
    // iterator = fetch.iterator();
    // while (iterator.hasNext()) {
    // iterator.next();
    // }
    //
    // query.field("owner").equal(laisson).get();
    //
    // GithubUser memberSince = ds.createQuery(GithubUser.class)
    // .field("memberSince").equal(date).get();
    // System.out.println("memberSince = " + memberSince);
    //
    // GithubUser since = ds.createQuery(GithubUser.class).field("since")
    // .equal(date).get();
    // System.out.println("since = " + since);
    // }
    //
    // @Test(dependsOnMethods = { "repositories" })
    // public void updates() {
    // laisson.followers = 12;
    // laisson.following = 68;
    // ds.save(laisson);
    // }
    //
    // @Test(dependsOnMethods = { "repositories" })
    // private void massUpdates() {
    // UpdateOperations<GithubUser> update = ds
    // .createUpdateOperations(GithubUser.class).inc("followers")
    // .set("following", 42);
    // Query<GithubUser> query = ds.createQuery(GithubUser.class)
    // .field("followers").equal(0);
    // ds.update(query, update);
    // }
    //
    // @Test(dependsOnMethods = { "repositories" }, expectedExceptions = {
    // ConcurrentModificationException.class })
    // public void versioned() {
    // Organization org01 = ds.createQuery(Organization.class).get();
    // Organization org02 = ds.createQuery(Organization.class).get();
    //
    // Assert.assertEquals(org01.version, 1L);
    // ds.save(org01);
    //
    // Assert.assertEquals(org01.version, 2L);
    // ds.save(org01);
    //
    // Assert.assertEquals(org01.version, 3L);
    // ds.save(org02);
    // }

}
