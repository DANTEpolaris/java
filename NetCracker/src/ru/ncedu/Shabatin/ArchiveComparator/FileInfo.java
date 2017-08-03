package ru.ncedu.Shabatin.ArchiveComparator;

/**
 * Representation of a file as a set of its distinctive properties.
 *
 * @author SHABATIN FILIP
 */
class FileInfo {
    private String name;
    private int hash;
    private long length;

    FileInfo(String name, int hash, long length) {
        setHash(hash);
        setLength(length);
        setName(name);
    }

    String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    int getHash() {
        return hash;
    }

    private void setHash(int hash) {
        this.hash = hash;
    }

    long getLength() {
        return length;
    }

    private void setLength(long length) {
        this.length = length;
    }
}
