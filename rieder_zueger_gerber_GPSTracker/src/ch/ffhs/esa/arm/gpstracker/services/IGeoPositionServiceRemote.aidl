package ch.ffhs.esa.arm.gpstracker.services;

interface IGeoPositionServiceRemote {
    String start();
    String pause();
    String stop();
    String configChanged();
}