package server.repository;

public interface Reposotory<T> {
    void save(T text);
    T load();
}
