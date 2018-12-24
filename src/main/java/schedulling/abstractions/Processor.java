package schedulling.abstractions;

public interface Processor {
    public void succesquued(Object key);

    public void errorquued(Object key);
}
